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
import dev.ktform.kt8s.container.components.VPAComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher
import dev.ktform.kt8s.container.fetchers.VpaVersionFetcher

data class VPAVersion(val vpaVersions: Map<VPAComponent, String> = emptyMap()) :
    Versions<VPAVersion>(vpaVersions.mapKeys { it.key as Component<VPAVersion> }) {
    companion object : VersionsFetcher<VPAVersion> by VpaVersionFetcher {
        fun String.toVpaVersion(): VPAVersion = VPAVersion(mapOf(VPAComponent.VPA to this))
    }
}
