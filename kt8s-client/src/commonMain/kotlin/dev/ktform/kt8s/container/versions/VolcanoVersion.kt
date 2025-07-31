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
import dev.ktform.kt8s.container.components.VolcanoComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher
import dev.ktform.kt8s.container.fetchers.VolcanoVersionFetcher

data class VolcanoVersion(val volcanoVersions: Map<VolcanoComponent, String> = emptyMap()) :
    Versions<VolcanoVersion>(volcanoVersions.mapKeys { it.key as Component<VolcanoVersion> }) {
    companion object : VersionsFetcher<VolcanoVersion> by VolcanoVersionFetcher {
        fun String.toVolcanoVersion(): VolcanoVersion =
            VolcanoVersion(mapOf(VolcanoComponent.Volcano to this))
    }
}
