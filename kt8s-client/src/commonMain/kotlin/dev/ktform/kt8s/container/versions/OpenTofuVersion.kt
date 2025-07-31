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
import dev.ktform.kt8s.container.components.OpenTofuComponent
import dev.ktform.kt8s.container.fetchers.OpenTofuVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class OpenTofuVersion(val openTofuVersions: Map<OpenTofuComponent, String> = emptyMap()) :
    Versions<OpenTofuVersion>(openTofuVersions.mapKeys { it.key as Component<OpenTofuVersion> }) {
    companion object : VersionsFetcher<OpenTofuVersion> by OpenTofuVersionFetcher {
        fun String.toOpenTofuVersion(): OpenTofuVersion =
            OpenTofuVersion(mapOf(OpenTofuComponent.OpenTofu to this))
    }
}
