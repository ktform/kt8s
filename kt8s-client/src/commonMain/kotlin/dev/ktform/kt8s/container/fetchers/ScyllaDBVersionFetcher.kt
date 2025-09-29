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

import arrow.core.*
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.ScyllaDBComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.versions.ScyllaDBVersion
import io.github.z4kn4fein.semver.toVersion
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object ScyllaDBVersionFetcher : VersionsFetcher<ScyllaDBVersion> {
    private const val SCYLLA_DB_RELEASE_PREFIX = "scylla-"

    @OptIn(ExperimentalTime::class)
    private val currentYear: String =
        Clock.System.now().toLocalDateTime(TimeZone.UTC).date.year.toString()

    override suspend fun getVersions(last: Int): Map<Component<ScyllaDBVersion>, List<String>> =
        ScyllaDBComponent.entries.associateWith { component ->
            repo(component)
                .fold(
                    { emptyList() },
                    { repo ->
                        when (component) {
                            ScyllaDBComponent.ScyllaDB -> {
                                val client = GithubClient()
                                client
                                    .getTags(repo)
                                    .map {
                                        it.filter { tag ->
                                                tag.startsWith(SCYLLA_DB_RELEASE_PREFIX) &&
                                                    !tag.startsWith(
                                                        SCYLLA_DB_RELEASE_PREFIX + currentYear
                                                    )
                                            }
                                            .map { tag ->
                                                tag.substringAfter(SCYLLA_DB_RELEASE_PREFIX)
                                            }
                                            .mapNotNull { s ->
                                                Either.catch { s.toVersion() }.getOrNull()
                                            }
                                            .filter { v ->
                                                !v.isPreRelease && (v.major == 0 || v.isStable)
                                            }
                                            .sortedDescending()
                                            .map { v -> v.toString() }
                                            .take(last)
                                    }
                                    .getOrElse { emptyList() }
                            }

                            else -> githubVersions(repo, limit = last).getOrElse { emptyList() }
                        }
                    },
                )
        }

    override fun repo(component: Component<ScyllaDBVersion>): Option<String> =
        when (component) {
            is ScyllaDBComponent if component == ScyllaDBComponent.ScyllaDB ->
                "https://github.com/scylladb/scylla".some()

            is ScyllaDBComponent if component == ScyllaDBComponent.ScyllaManager ->
                "https://github.com/scylladb/scylla-manager".some()

            is ScyllaDBComponent if component == ScyllaDBComponent.ScyllaOperator ->
                "https://github.com/scylladb/scylla-operator".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<ScyllaDBVersion>): Option<String> =
        when (component) {
            is ScyllaDBComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<ScyllaDBVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is ScyllaDBComponent if this == ScyllaDBComponent.ScyllaDB ->
                listOf("6.2.3", "6.2.2", "6.2.1", "6.2.0", "6.1.5")
            is ScyllaDBComponent if this == ScyllaDBComponent.ScyllaManager ->
                listOf("3.6.0", "3.5.1", "3.5.0", "3.4.2", "3.4.1")
            is ScyllaDBComponent if this == ScyllaDBComponent.ScyllaOperator ->
                listOf("1.18.1", "1.18.0", "1.17.1", "1.17.0", "1.16.3")
            else -> emptyList()
        }
}
