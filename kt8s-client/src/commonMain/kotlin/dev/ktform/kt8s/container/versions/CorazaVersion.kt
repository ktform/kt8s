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
import dev.ktform.kt8s.container.components.CorazaComponent
import dev.ktform.kt8s.container.fetchers.CorazaVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class CorazaVersion(val corazaVersions: Map<CorazaComponent, String> = emptyMap()) :
    Versions<CorazaVersion>(corazaVersions.mapKeys { it.key as Component<CorazaVersion> }) {
    companion object : VersionsFetcher<CorazaVersion> by CorazaVersionFetcher {
        fun String.toCorazaVersion(): CorazaVersion =
            CorazaVersion(mapOf(CorazaComponent.Coraza to this))
    }
}
