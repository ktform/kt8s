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
import dev.ktform.kt8s.container.components.GolangComponent
import dev.ktform.kt8s.container.fetchers.GolangVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class GolangVersion(val golangVersions: Map<GolangComponent, String> = emptyMap()) :
    Versions<GolangVersion>(golangVersions.mapKeys { it.key as Component<GolangVersion> }) {
    companion object : VersionsFetcher<GolangVersion> by GolangVersionFetcher {
        fun String.toGolangVersion(): GolangVersion =
            GolangVersion(mapOf(GolangComponent.Golang to this))
    }
}
