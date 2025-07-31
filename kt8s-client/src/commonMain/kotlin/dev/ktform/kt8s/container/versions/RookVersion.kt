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
import dev.ktform.kt8s.container.components.RookComponent
import dev.ktform.kt8s.container.fetchers.RookVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class RookVersion(val rookVersions: Map<RookComponent, String> = emptyMap()) :
    Versions<RookVersion>(rookVersions.mapKeys { it.key as Component<RookVersion> }) {
    companion object : VersionsFetcher<RookVersion> by RookVersionFetcher {
        fun String.toRookVersion(): RookVersion = RookVersion(mapOf(RookComponent.Rook to this))
    }
}
