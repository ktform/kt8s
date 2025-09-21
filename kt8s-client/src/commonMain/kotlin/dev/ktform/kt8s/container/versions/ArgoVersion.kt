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

import dev.ktform.kt8s.container.components.ArgoComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.ArgoVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class ArgoVersion(val argoVersions: Map<ArgoComponent, String> = emptyMap()) :
    Versions<ArgoVersion>(argoVersions.mapKeys { it.key as Component<ArgoVersion> }) {
    fun plus(other: ArgoVersion): ArgoVersion = ArgoVersion(argoVersions + other.argoVersions)

    companion object : VersionsFetcher<ArgoVersion> by ArgoVersionFetcher {
        fun String.toArgoCdVersion(): ArgoVersion = ArgoVersion(mapOf(ArgoComponent.ArgoCd to this))

        fun String.toArgoRolloutsVersion(): ArgoVersion =
            ArgoVersion(mapOf(ArgoComponent.ArgoRollouts to this))

        fun String.toArgoWorkflowsVersion(): ArgoVersion =
            ArgoVersion(mapOf(ArgoComponent.ArgoWorkflows to this))

        fun String.toArgoEventsVersion(): ArgoVersion =
            ArgoVersion(mapOf(ArgoComponent.ArgoEvents to this))
    }
}
