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
import dev.ktform.kt8s.container.components.TheiaComponent
import dev.ktform.kt8s.container.fetchers.TheiaVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class TheiaVersion(val theiaVersions: Map<TheiaComponent, String> = emptyMap()) :
    Versions<TheiaVersion>(theiaVersions.mapKeys { it.key as Component<TheiaVersion> }) {
    companion object : VersionsFetcher<TheiaVersion> by TheiaVersionFetcher {
        fun String.toTheiaVersion(): TheiaVersion =
            TheiaVersion(mapOf(TheiaComponent.Theia to this))
    }
}
