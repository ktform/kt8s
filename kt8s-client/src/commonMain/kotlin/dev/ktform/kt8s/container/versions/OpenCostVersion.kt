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
import dev.ktform.kt8s.container.components.OpenCostComponent
import dev.ktform.kt8s.container.fetchers.OpenCostVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class OpenCostVersion(val openCostVersions: Map<OpenCostComponent, String> = emptyMap()) :
    Versions<OpenCostVersion>(openCostVersions.mapKeys { it.key as Component<OpenCostVersion> }) {
    companion object : VersionsFetcher<OpenCostVersion> by OpenCostVersionFetcher {
        fun String.toOpenCostVersion(): OpenCostVersion =
            OpenCostVersion(mapOf(OpenCostComponent.OpenCost to this))
    }
}
