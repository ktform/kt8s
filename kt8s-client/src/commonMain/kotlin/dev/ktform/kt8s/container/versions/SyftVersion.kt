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
import dev.ktform.kt8s.container.components.SyftComponent
import dev.ktform.kt8s.container.fetchers.SyftVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class SyftVersion(val syftVersions: Map<SyftComponent, String> = emptyMap()) :
    Versions<SyftVersion>(syftVersions.mapKeys { it.key as Component<SyftVersion> }) {
    companion object : VersionsFetcher<SyftVersion> by SyftVersionFetcher {
        fun String.toSyftVersion(): SyftVersion = SyftVersion(mapOf(SyftComponent.Syft to this))
    }
}
