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
import dev.ktform.kt8s.container.components.PostgreSQLComponent
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.versions.PostgreSQLVersion

object PostgreSQLVersionFetcher : VersionsFetcher<PostgreSQLVersion> {
    const val RELEASE_PREFIX = "REL_"

    override suspend fun getVersions(last: Int): Map<Component<PostgreSQLVersion>, List<String>> =
        PostgreSQLComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                val client = GithubClient()
                client
                    .getTags(repo)
                    .map { all ->
                        all.filter { v -> v.startsWith(RELEASE_PREFIX) }
                            .map { v -> v.substringAfter(RELEASE_PREFIX).replace("_", ".") }
                            .filter { v ->
                                !v.lowercase().contains("beta") && !v.lowercase().contains("rc")
                            }
                            .sortedDescending()
                    }
                    .getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<PostgreSQLVersion>): Option<String> =
        when (component) {
            is PostgreSQLComponent if component == PostgreSQLComponent.PostgreSQL ->
                "https://github.com/postgres/postgres".some()

            is PostgreSQLComponent if component == PostgreSQLComponent.CNPG ->
                "https://github.com/cloudnative-pg/cloudnative-pg".some()

            is PostgreSQLComponent if component == PostgreSQLComponent.Stackgres ->
                "https://github.com/ongres/stackgres".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<PostgreSQLVersion>): Option<String> =
        when (component) {
            is PostgreSQLComponent -> "$RELEASE_PREFIX$this".some()
            else -> None
        }

    override fun Component<PostgreSQLVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is PostgreSQLComponent if this == PostgreSQLComponent.PostgreSQL ->
                listOf("17.5", "17.4")

            is PostgreSQLComponent if this == PostgreSQLComponent.CNPG -> listOf()
            is PostgreSQLComponent if this == PostgreSQLComponent.Stackgres -> listOf()
            else -> emptyList()
        }
}
