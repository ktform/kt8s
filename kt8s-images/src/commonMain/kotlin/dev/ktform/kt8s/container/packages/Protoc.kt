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
package dev.ktform.kt8s.container.packages

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.ProtocVersionFetcher
import dev.ktform.kt8s.container.versions.ProtocVersion

class Protoc(val versions: ProtocVersion) : Renderable {

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, ProtocVersionFetcher, env)

    companion object {
        const val REPO = "https://github.com/protocolbuffers/protobuf"

        val DEFAULT_VERSIONS = listOf("31.1", "31.0", "30.2")

        val `package` =
            Package(
                packageName = "protoc"
                // repo = REPO,
                // repoVersion = { version, toRepo ->
                //   if (toRepo) {
                //     "v${version}"
                //   } else {
                //     version
                //   }
                // },
                // availableVersions = { _ ->
                //   val client = GithubClient()
                //   client.getTags(REPO).map { all ->
                //     all.filter { v -> v.startsWith("v") }
                //       .map { v -> v.substringAfter("v").trim()}
                //       .filter { v -> !v.lowercase().contains("beta") &&
                // !v.lowercase().contains("rc") && !v.lowercase().contains("dev") }
                //       .sortedWith(
                //         run {
                //           fun numAt(s: String, idx: Int): Int = s
                //             .split('.', '-', '_')
                //             .getOrNull(idx)
                //             ?.takeWhile(Char::isDigit)
                //             ?.toIntOrNull() ?: 0

                //           compareByDescending<String> { numAt(it, 0) }
                //             .thenByDescending { numAt(it, 1) }
                //             .thenByDescending { numAt(it, 2) }
                //             .thenByDescending { numAt(it, 3) }
                //         }
                //       )
                //   }
                // }
            )
    }
}
