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
import dev.ktform.kt8s.container.components.TempoComponent
import dev.ktform.kt8s.container.fetchers.TempoVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class TempoVersion(val tempoVersions: Map<TempoComponent, String> = emptyMap()) :
    Versions<TempoVersion>(tempoVersions.mapKeys { it.key as Component<TempoVersion> }) {
    companion object : VersionsFetcher<TempoVersion> by TempoVersionFetcher {
        fun String.toTempoVersion(): TempoVersion =
            TempoVersion(mapOf(TempoComponent.Tempo to this))
    }
}
