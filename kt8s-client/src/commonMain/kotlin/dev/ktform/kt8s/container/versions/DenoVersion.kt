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
import dev.ktform.kt8s.container.components.DenoComponent
import dev.ktform.kt8s.container.fetchers.DenoVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class DenoVersion(val denoVersions: Map<DenoComponent, String> = emptyMap()) :
    Versions<DenoVersion>(denoVersions.mapKeys { it.key as Component<DenoVersion> }) {
    companion object : VersionsFetcher<DenoVersion> by DenoVersionFetcher {
        fun String.toDenoVersion(): DenoVersion = DenoVersion(mapOf(DenoComponent.Deno to this))
    }
}
