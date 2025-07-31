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
import dev.ktform.kt8s.container.components.FalcoComponent
import dev.ktform.kt8s.container.fetchers.FalcoVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class FalcoVersion(val falcoVersions: Map<FalcoComponent, String> = emptyMap()) :
    Versions<FalcoVersion>(falcoVersions.mapKeys { it.key as Component<FalcoVersion> }) {
    companion object : VersionsFetcher<FalcoVersion> by FalcoVersionFetcher {
        fun String.toFalcoVersion(): FalcoVersion =
            FalcoVersion(mapOf(FalcoComponent.Falco to this))
    }
}
