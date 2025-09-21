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
package dev.ktform.kt8s.compiler

import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import com.charleskorn.kaml.*
import dev.ktform.kt8s.compiler.JSONSchema.Definition
import dev.ktform.kt8s.compiler.JSONSchema.toDefinition
import java.net.JarURLConnection
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.asSequence
import kotlinx.serialization.json.*

object Crds {

    internal fun chartFiles(
        classLoader: ClassLoader = this::class.java.classLoader
    ): Map<String, Map<String, YamlNode>> =
        classLoader.getResource("crds")?.let { resource ->
            when (resource.protocol) {
                "file" -> loadFromFileSystem(resource)
                "jar" -> loadFromJar(resource)
                else -> throw IllegalArgumentException("Unsupported protocol: ${resource.protocol}")
            }
        } ?: emptyMap()

    private fun loadFromFileSystem(resource: java.net.URL): Map<String, Map<String, YamlNode>> =
        Files.list(Paths.get(resource.toURI())).use { chartStream ->
            chartStream
                .filter { Files.isDirectory(it) }
                .asSequence()
                .associate { chartDir ->
                    val chartName = chartDir.fileName.toString()
                    val files =
                        Files.list(chartDir).use { fileStream ->
                            fileStream
                                .filter {
                                    it.toString().endsWith(".yaml") ||
                                        it.toString().endsWith(".yml")
                                }
                                .asSequence()
                                .mapNotNull { yamlFile ->
                                    catch({
                                        val fileName = yamlFile.fileName.toString()
                                        val yamlNode =
                                            Yaml.default.parseToYamlNode(Files.readString(yamlFile))
                                        fileName to yamlNode
                                    }) {
                                        println("Error parsing $yamlFile")
                                        null
                                    }
                                }
                                .associate { it }
                        }
                    chartName to files
                }
                .filterValues { it.isNotEmpty() }
        }

    private fun loadFromJar(resource: java.net.URL): Map<String, Map<String, YamlNode>> {
        val jarConnection = resource.openConnection() as JarURLConnection
        val jarFile = jarConnection.jarFile

        return jarFile
            .entries()
            .asSequence()
            .filter { entry ->
                !entry.isDirectory &&
                    entry.name.startsWith("crds/") &&
                    (entry.name.endsWith(".yaml") || entry.name.endsWith(".yml")) &&
                    entry.name.count { it == '/' } == 2
            }
            .groupBy { it.name.split("/")[1] } // Group by chart name
            .mapValues { (yamlFile, entries) ->
                entries
                    .mapNotNull { entry ->
                        catch({
                            val fileName = entry.name.split("/")[2]
                            val content =
                                jarFile.getInputStream(entry).use {
                                    it.readBytes().decodeToString()
                                }
                            val yamlNode = Yaml.default.parseToYamlNode(content)
                            fileName to yamlNode
                        }) {
                            println("Error parsing $yamlFile")
                            null
                        }
                    }
                    .associate { it }
            }
    }

    fun all(
        classLoader: ClassLoader = this::class.java.classLoader
    ): Either<String, Map<String, List<Definition>>> = either {
        chartFiles(classLoader).mapValues { (chartName, chartFiles) ->
            chartFiles.entries.mapNotNull { (fileName, yamlNode) ->
                try {
                    // Convert YAML to JSON
                    val jsonNode = yamlToJson(yamlNode)

                    // Extract CRD schema from the YAML structure
                    val crdJson = jsonNode as? JsonObject ?: return@mapNotNull null
                    val spec = crdJson["spec"]?.jsonObject ?: return@mapNotNull null
                    val versions = spec["versions"]?.jsonArray ?: return@mapNotNull null

                    // Get the first version's schema (usually the latest/preferred version)
                    val version = versions.firstOrNull()?.jsonObject ?: return@mapNotNull null
                    val schema =
                        version["schema"]?.jsonObject?.get("openAPIV3Schema")?.jsonObject
                            ?: return@mapNotNull null

                    // Extract metadata for the definition name
                    val metadata = crdJson["metadata"]?.jsonObject
                    val crdName =
                        metadata?.get("name")?.jsonPrimitive?.content
                            ?: fileName.removeSuffix(".yaml")
                    val group = spec["group"]?.jsonPrimitive?.content ?: ""
                    val kind = spec["names"]?.jsonObject?.get("kind")?.jsonPrimitive?.content ?: ""
                    val versionName = version["name"]?.jsonPrimitive?.content ?: "v1"

                    // Convert the schema to a Definition using JSONSchema parsing
                    val definitionName = "$group.$versionName.$kind"
                    schema.toDefinition(definitionName).getOrNull()
                } catch (e: Exception) {
                    println("Error processing CRD $fileName: ${e.message}")
                    null
                }
            }
        }
    }

    private fun yamlToJson(node: YamlNode): JsonElement =
        when (node) {
            is YamlScalar ->
                when (val content = node.content) {
                    "true",
                    "false" -> JsonPrimitive(content.toBoolean())
                    "null" -> JsonNull
                    else -> {
                        content.toIntOrNull()?.let { JsonPrimitive(it) }
                            ?: content.toDoubleOrNull()?.let { JsonPrimitive(it) }
                            ?: JsonPrimitive(content)
                    }
                }

            is YamlList -> buildJsonArray { node.items.forEach { add(yamlToJson(it)) } }

            is YamlMap ->
                buildJsonObject {
                    node.entries.forEach { (key, value) ->
                        val keyStr = key.content
                        put(keyStr, yamlToJson(value))
                    }
                }

            is YamlNull -> JsonNull
            is YamlTaggedNode -> yamlToJson(node.innerNode)
        }
}
