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
import dev.ktform.kt8s.container.components.TektonComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.TektonVersion

object TektonVersionFetcher : VersionsFetcher<TektonVersion> {
    override suspend fun getVersions(last: Int): Map<Component<TektonVersion>, List<String>> =
        TektonComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<TektonVersion>): Option<String> =
        when (component) {
            is TektonComponent if component == TektonComponent.TektonPipeline ->
                "https://github.com/tektoncd/pipeline".some()

            is TektonComponent if component == TektonComponent.TektonTriggers ->
                "https://github.com/tektoncd/triggers".some()

            is TektonComponent if component == TektonComponent.TektonDashboard ->
                "https://github.com/tektoncd/dashboard".some()

            is TektonComponent if component == TektonComponent.TektonChains ->
                "https://github.com/tektoncd/chains".some()

            is TektonComponent if component == TektonComponent.TektonResults ->
                "https://github.com/tektoncd/results".some()

            is TektonComponent if component == TektonComponent.TektonCli ->
                "https://github.com/tektoncd/cli".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<TektonVersion>): Option<String> =
        when (component) {
            is TektonComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<TektonVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is TektonComponent if TektonComponent.TektonPipeline == this ->
                listOf("1.3.1", "1.3.0", "1.2.0")

            is TektonComponent if TektonComponent.TektonTriggers == this ->
                listOf("0.32.0", "0.31.0")

            is TektonComponent if TektonComponent.TektonDashboard == this ->
                listOf("0.60.0", "0.59.0", "0.58.0")

            is TektonComponent if TektonComponent.TektonChains == this -> listOf("0.41.1", "0.41.0")
            is TektonComponent if TektonComponent.TektonResults == this ->
                listOf("0.15.2", "0.15.1")

            is TektonComponent if TektonComponent.TektonCli == this -> listOf("0.41.1", "0.41.0")
            else -> emptyList()
        }
}
