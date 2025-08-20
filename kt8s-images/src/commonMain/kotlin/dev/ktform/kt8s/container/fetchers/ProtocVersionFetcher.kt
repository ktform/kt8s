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
package dev.ktform.kt8s.container.fetchers

import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.ProtocComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.versions.ProtocVersion

object ProtocVersionFetcher : VersionsFetcher<ProtocVersion> {
    override suspend fun getVersions(last: Int): Map<Component<ProtocVersion>, List<String>> =
        ProtocComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                val client = GithubClient()
                client.getTags(repo).map { all ->
                    all.filter { v -> v.startsWith("v") }
                        .map { v -> v.substringAfter("v").trim() }
                        .filter { v ->
                            !v.lowercase().contains("beta") &&
                                !v.lowercase().contains("rc") &&
                                !v.lowercase().contains("dev")
                        }
                        .sortedWith(
                            run {
                                fun numAt(s: String, idx: Int): Int =
                                    s.split('.', '-', '_')
                                        .getOrNull(idx)
                                        ?.takeWhile(Char::isDigit)
                                        ?.toIntOrNull() ?: 0

                                compareByDescending<String> { n -> numAt(n, 0) }
                                    .thenByDescending { n -> numAt(n, 1) }
                                    .thenByDescending { n -> numAt(n, 2) }
                                    .thenByDescending { n -> numAt(n, 3) }
                            }
                        )
                }
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<ProtocVersion>): Option<String> =
        when (component) {
            is ProtocComponent if component == ProtocComponent.Protoc ->
                "https://github.com/protocolbuffers/protobuf".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<ProtocVersion>): Option<String> =
        when (component) {
            is ProtocComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<ProtocVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is ProtocComponent -> listOf()
            else -> emptyList()
        }
}
