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
package dev.ktform.kt8s.container.versions

import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.TektonComponent
import dev.ktform.kt8s.container.fetchers.TektonVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class TektonVersion(val argoVersions: Map<TektonComponent, String> = emptyMap()) :
    Versions<TektonVersion>(argoVersions.mapKeys { it.key as Component<TektonVersion> }) {
    fun plus(other: TektonVersion): TektonVersion = TektonVersion(argoVersions + other.argoVersions)

    companion object : VersionsFetcher<TektonVersion> by TektonVersionFetcher {
        fun String.toTektonChainsVersion(): TektonVersion =
            TektonVersion(mapOf(TektonComponent.TektonChains to this))

        fun String.toTektonCliVersion(): TektonVersion =
            TektonVersion(mapOf(TektonComponent.TektonCli to this))

        fun String.toTektonDashboardVersion(): TektonVersion =
            TektonVersion(mapOf(TektonComponent.TektonDashboard to this))

        fun String.toTektonPipelineVersion(): TektonVersion =
            TektonVersion(mapOf(TektonComponent.TektonPipeline to this))

        fun String.toTektonTriggersVersion(): TektonVersion =
            TektonVersion(mapOf(TektonComponent.TektonTriggers to this))

        fun String.toTektonResultsVersion(): TektonVersion =
            TektonVersion(mapOf(TektonComponent.TektonResults to this))
    }
}
