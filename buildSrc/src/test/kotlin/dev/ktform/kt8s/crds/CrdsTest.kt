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

import com.varabyte.truthish.assertThat
import com.varabyte.truthish.assertWithMessage
import com.varabyte.truthish.assertWithMessage
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.io.path.createTempDirectory
import kotlin.test.Test

class CrdsTest {

  @Test
  fun testTemplateCrds() {
    assertWithMessage("Crds should not be empty").that(Crds.all).isNotEmpty()

    Crds.all.forEach { crds ->
      val contents = crds.rawContents()
      val crdsName = crds.javaClass.simpleName.replace("Crds", "")
      assertWithMessage(crdsName).that(contents).isNotEmpty()

      contents.forEach { (name, reader) ->
        reader.use { r ->
          val content = r.readText()
          assertWithMessage("$crdsName $name").that(content).isNotEmpty()
        }
      }
    }
  }

  @Test
  fun templateHelmChart() {
    val (repoName, _) = ArgoCrds.helmRepo.get()
    val chartName = ArgoCrds.chartName.get()
    val process = Crds.templateHelmCrds(repoName, chartName)

    try {
      val result = process.inputStream.bufferedReader().use { it.readText() }
      assertThat(result).isNotEmpty()
      assert(process.waitFor(60, TimeUnit.SECONDS))
      assertThat(process.exitValue()).isEqualTo(0)
    } finally {
      process.destroyForcibly()
    }
  }

  @Test
  fun testFilterCrdsFromChart() {
    val input = """
    apiVersion: apiextensions.k8s.io/v1
    kind: CustomResourceDefinition
    spec:
      names:
        kind: Test1
    ---
    apiVersion: apiextensions.k8s.io/v1
    kind: CustomResourceDefinition
    spec:
      names:
        kind: Test2
    ---
    """.trimIndent()

    val content = Crds.filterCrdsFromChart("TestChart", input.reader().buffered())
    assertThat(content.keys).isEqualTo(setOf("Test1.yaml", "Test2.yaml"))
    content.values.forEach { it.close() } // Close the readers
  }

  @Test
  fun testWriteAllCrdsToEmptyDirectory() {
    val tempDir = createTempDirectory("crds-test")
    val targetDir = File(tempDir.toString())
    targetDir.mkdirs()

    try {
      Crds.writeAllCrds(targetDir)

      // Ensure all files are created and not empty
      val files = targetDir.walkTopDown().filter { it.isFile }.toList()
      assertWithMessage("Target directory should contain files").that(files).isNotEmpty()

      files.forEach { file ->
        val content = file.readText()
        assertWithMessage("File ${file.name} should not be empty").that(content).isNotEmpty()
        assertWithMessage("Incomplete $file").that(content.contains("served: ")).isTrue()
        assertWithMessage("Incomplete $file").that(content.contains("storage: ")).isTrue()
      }
    } finally {
      targetDir.deleteRecursively()
    }
  }
}