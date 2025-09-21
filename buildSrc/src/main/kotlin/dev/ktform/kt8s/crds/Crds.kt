/*
 * Copyright (C) 2016-2025 Yuriy Yarosh
 * All rights reserved.
 *
 * SPDX-License-Identifier: MPL-2.0
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.ktform.kt8s.crds

import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import java.io.BufferedReader
import java.io.File
import java.io.StringWriter
import java.net.URI
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

sealed interface Crds {
  val helmRepo: Optional<Pair<String, String>> get() = Optional.empty()
  val chartName: Optional<String> get() = Optional.empty()
  val crdUrls: Map<String, String> get() = emptyMap()

  fun rawContents(): Map<String, BufferedReader> = when {
    helmRepo.isPresent && chartName.isPresent -> {
      val (repoName, repoUrl) = helmRepo.get()
      val chart = chartName.get()
      if (!repoUrl.startsWith("oci:")) helmRepoAdd(repoName, repoUrl)

      val process = templateHelmCrds(repoName, chart)
      try {
        process.inputStream.bufferedReader().use { reader ->
          val crds = filterCrdsFromChart(chart, reader)
          if (!process.waitFor(60, TimeUnit.SECONDS) || process.exitValue() != 0) {
            crds.values.forEach { it.close() }
            throw RuntimeException("Failed to render $chart helm chart")
          }
          crds
        }
      } finally {
        process.destroyForcibly()
      }
    }

    crdUrls.isNotEmpty() -> {
      if (crdUrls.size == 1 && crdUrls.keys.first().isEmpty()) {
        val url = crdUrls.values.first()
        URI(url).toURL().openStream().bufferedReader().use { reader ->
          filterCrdsFromChart(this.javaClass.simpleName.replace("Crds", ""), reader)
        }
      } else {
        crdUrls.mapValues { (_, url) ->
          URI(url).toURL().openStream().bufferedReader()
        }
      }
    }

    else -> emptyMap()
  }

  companion object {
    private val helmRepoCache: MutableSet<String> = ConcurrentHashMap.newKeySet()

    val all = listOf(
      ArgoCrds, ArgoEventsCrds, ArgoRolloutsCrds, ArgoWorkflowsCrds, AtlasOperatorCrds,
      CassandraCrds, CertManagerCrds, ChaosMeshCrds, CiliumCrds, CnpgCrds,
      ExternalDnsCrds, ExternalSecrets, FalcoCrds, GatewayApiCrds, KedaCrds,
      KyvernoCrds, MinioCrds, PrometheusCrds, StrimziCrds, TektonDashboardCrds,
      TektonOperatorCrds, TektonPipelineCrds, TektonTriggersCrds, TheiaCrds,
      VeleroCrds, VolcanoCrds, VpaCrds,
    )

    fun writeAllCrds(dir: File) {
      // Add all unique helm repos first
      all.mapNotNull { it.helmRepo.orElse(null) }
        .distinctBy { it.first }
        .filterNot { it.second.startsWith("oci:") }
        .forEach { (repoName, repoUrl) -> helmRepoAdd(repoName, repoUrl) }

      val executor = Executors.newCachedThreadPool()
      try {
        // Process all CRDs in parallel
        val futures = all.map { crds ->
          CompletableFuture.runAsync({
            val subDir = crds.javaClass.simpleName.replace("Crds", "").toDefaultLowerCase()
            val targetDir = dir.resolve(subDir).apply { mkdirs() }

            if (targetDir.listFiles()?.isEmpty() ?: true) {
              crds.rawContents().forEach { (name, reader) ->
                reader.use { r ->
                  val file = File(targetDir, name)
                  file.bufferedWriter().use { writer ->
                    r.copyTo(writer)
                  }
                  if (file.length() == 0L) {
                    file.delete()
                    throw RuntimeException("Got empty content for $name")
                  }
                }
              }
            }
          }, executor)
        }

        CompletableFuture.allOf(*futures.toTypedArray()).get(10, TimeUnit.MINUTES)
      } finally {
        executor.shutdown()
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
          executor.shutdownNow()
        }
      }
    }

    fun filterCrdsFromChart(chartName: String, reader: BufferedReader): Map<String, BufferedReader> {
      val crds = mutableMapOf<String, BufferedReader>()
      val documentBuilder = StringWriter()
      var index = 0

      reader.use { r ->
        r.lineSequence().forEach { line ->
          if (line.trim() == "---") {
            val document = documentBuilder.toString()
            if (document.contains("kind: CustomResourceDefinition", ignoreCase = false)) {
              val trimmed = document.trim()
              if (trimmed.isNotEmpty()) {
                val name = extractCrdKind(trimmed, index++)
                crds["$name.yaml"] = trimmed.reader().buffered()
              }
            }
            documentBuilder.buffer.setLength(0)
          } else {
            documentBuilder.append(line).append("\n")
          }
        }

        // Handle the last document
        val lastDocument = documentBuilder.toString()
        if (lastDocument.isNotBlank() && lastDocument.contains("kind: CustomResourceDefinition", ignoreCase = false)) {
          val trimmed = lastDocument.trim()
          if (trimmed.isNotEmpty()) {
            val name = extractCrdKind(trimmed, index)
            crds["$name.yaml"] = trimmed.reader().buffered()
          }
        }
      }

      if (crds.isEmpty()) {
        throw RuntimeException("No CRDs found in $chartName helm chart")
      }

      return crds
    }

    private fun extractCrdKind(chart: String, index: Int, offset: Int = 0): String {
      val nameIndex = chart.indexOf("kind: ", offset)
      if (nameIndex == -1) return "crd-$index"

      val lineStart = nameIndex + "kind: ".length
      val lineEnd = chart.indexOf('\n', lineStart).takeIf { it != -1 } ?: chart.length
      val name = chart.substring(lineStart, lineEnd).trim()

      return if (name == "CustomResourceDefinition") {
        extractCrdKind(chart, index, lineEnd + 1)
      } else {
        name
      }
    }

    fun templateHelmCrds(repoName: String, chartName: String): Process {
      val args = if (chartName == "cert-manager") {
        arrayOf("--include-crds", "--set", "crds.enabled=true")
      } else {
        arrayOf("--include-crds", "--set", "installCRDs=true", "--set", "crds.enabled=true")
      }

      val command = if (chartName.startsWith("oci://")) {
        listOf("helm", "template", chartName) + args
      } else {
        listOf("helm", "template", "$repoName/$chartName") + args
      }

      return ProcessBuilder(command).redirectErrorStream(true).start()
    }

    private fun helmRepoExists(repoName: String, repoUrl: String): Boolean {
      val cacheKey = "$repoName|$repoUrl"
      if (cacheKey in helmRepoCache) return true

      val process = ProcessBuilder(listOf("helm", "repo", "list")).redirectErrorStream(true).start()
      try {
        val output = process.inputStream.bufferedReader().use { it.readText() }
        if (!process.waitFor(5, TimeUnit.SECONDS) || process.exitValue() != 0) {
          return false
        }

        val exists = output.lines().any { line ->
          val parts = line.split('\t')
          parts.size >= 2 && parts[0].trim() == repoName && parts[1].trim() == repoUrl
        }

        if (exists) helmRepoCache.add(cacheKey)
        return exists
      } catch (e: Exception) {
        println("Failed to check helm repo $repoName $repoUrl: ${e.message}")
        return false
      } finally {
        process.destroyForcibly()
      }
    }

    private fun helmRepoAdd(repoName: String, repoUrl: String) {
      if (helmRepoExists(repoName, repoUrl)) return

      val process = ProcessBuilder(listOf("helm", "repo", "add", repoName, repoUrl))
        .redirectErrorStream(true)
        .start()

      try {
        if (!process.waitFor(30, TimeUnit.SECONDS) || process.exitValue() != 0) {
          val error = process.inputStream.bufferedReader().use { it.readText() }
          throw RuntimeException("Error adding helm repo '$repoName' from '$repoUrl': $error")
        }
        helmRepoCache.add("$repoName|$repoUrl")
      } finally {
        process.destroyForcibly()
      }
    }
  }
}
