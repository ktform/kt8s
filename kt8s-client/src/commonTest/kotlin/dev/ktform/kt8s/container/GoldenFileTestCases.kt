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
package dev.ktform.kt8s.dev.ktform.kt8s.container

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.right
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.Serializable

@Serializable
object GoldenFileTestCases {

    const val TARGET_DIR = "src/commonTest/resources/containers"

    private fun readFile(
        goldenFileName: String,
        goldenFile: Path = Path("$TARGET_DIR/$goldenFileName"),
    ): Either<String, String> =
        if (!SystemFileSystem.exists(goldenFile)) {
            goldenFile.parent?.let { dir ->
                if (!SystemFileSystem.exists(dir)) SystemFileSystem.createDirectories(dir)
            }
            "".right()
        } else {
            Either.catch {
                    SystemFileSystem.source(goldenFile).use { source ->
                        source.buffered().readByteArray().decodeToString()
                    }
                }
                .mapLeft { "Failed to read golden file: ${it.message}" }
        }

    private fun writeFile(
        content: String,
        goldenFileName: String,
        goldenFile: Path = Path("$TARGET_DIR/$goldenFileName"),
    ): Either<String, Unit> =
        Either.catch {
                val bytes = content.encodeToByteArray()
                SystemFileSystem.sink(goldenFile).use { sink ->
                    val buffer = sink.buffered()
                    buffer.write(bytes, 0, bytes.size)
                    buffer.flush()
                }
            }
            .mapLeft { "Failed to write golden file: ${it.message}" }

    fun String.getOrUpdateExpected(goldenFileName: String) =
        readFile(goldenFileName)
            .map { expected ->
                expected.ifEmpty {
                    writeFile(this, goldenFileName)
                    this
                }
            }
            .getOrElse { throw Exception("Failed to get test case: $it") }
}
