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
package dev.ktform.kt8s.container

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.right
import arrow.core.toOption
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class GoldenFileJsonTestCases(val cases: MutableMap<String, String> = mutableMapOf()) {
    companion object {
        const val TARGET_DIR = "src/commonTest/resources/containers"

        private val json = Json { prettyPrint = true }

        private fun readGoldenFile(
            goldenFileName: String,
            goldenFile: Path = Path("${GoldenFileTestCases.TARGET_DIR}/$goldenFileName"),
        ): Either<String, GoldenFileJsonTestCases> =
            if (!SystemFileSystem.exists(goldenFile)) {
                goldenFile.parent?.let { dir ->
                    if (!SystemFileSystem.exists(dir)) SystemFileSystem.createDirectories(dir)
                }
                GoldenFileJsonTestCases().right()
            } else {
                Either.catch {
                        val content =
                            SystemFileSystem.source(goldenFile).use { source ->
                                source.buffered().readByteArray().decodeToString()
                            }
                        try {
                            json.decodeFromString<GoldenFileJsonTestCases>(content)
                        } catch (e: Exception) {
                            throw Exception(
                                "Failed to parse JSON content: '${content.take(100)}...' - ${e.message}",
                                e,
                            )
                        }
                    }
                    .mapLeft { "Failed to read golden file: ${it.message}" }
            }

        private fun writeGoldenFile(
            cases: GoldenFileJsonTestCases,
            goldenFileName: String,
            goldenFile: Path = Path("$TARGET_DIR/$goldenFileName"),
        ): Either<String, Unit> =
            Either.catch {
                    val jsonString = json.encodeToString(cases)
                    val bytes = jsonString.encodeToByteArray()
                    SystemFileSystem.sink(goldenFile).use { sink ->
                        val buffer = sink.buffered()
                        buffer.write(bytes, 0, bytes.size)
                        buffer.flush()
                    }
                }
                .mapLeft { "Failed to write golden file: ${it.message}" }

        fun String.getOrUpdateExpectedJson(key: String, goldenFileName: String) =
            readGoldenFile(goldenFileName)
                .map { cases ->
                    cases.cases[key]
                        .toOption()
                        .fold(
                            ifEmpty = {
                                cases.cases[key] = this
                                writeGoldenFile(cases, goldenFileName)
                                    .fold(
                                        { println("Warning: $it") },
                                        { println("Added new golden test case: $key") },
                                    )
                                this
                            },
                            ifSome = { expected ->
                                if (expected != this) {
                                    println("Golden file mismatch for key '$key':")
                                    println("Expected:\n$expected")
                                    println("Actual:\n$this")
                                    println(
                                        "To update golden file, delete the entry for '$key' and re-run tests"
                                    )
                                }
                                expected
                            },
                        )
                }
                .getOrElse { throw Exception("Failed to get golden test case: $it") }
    }
}
