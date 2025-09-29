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
import dev.ktform.kt8s.container.components.PrometheusComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.PrometheusVersion

object PrometheusVersionFetcher : VersionsFetcher<PrometheusVersion> {
    override suspend fun getVersions(last: Int): Map<Component<PrometheusVersion>, List<String>> =
        PrometheusComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<PrometheusVersion>): Option<String> =
        when (component) {
            is PrometheusComponent if component == PrometheusComponent.Prometheus ->
                "https://github.com/prometheus/prometheus".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<PrometheusVersion>): Option<String> =
        when (component) {
            is PrometheusComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<PrometheusVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is PrometheusComponent -> listOf("3.6.0", "3.5.0", "3.4.2", "3.4.1", "3.4.0")
            else -> emptyList()
        }
}
