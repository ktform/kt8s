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
import dev.ktform.kt8s.container.components.DataFusionBallistaComponent
import dev.ktform.kt8s.container.fetchers.DataFusionBallistaVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class DataFusionBallistaVersion(
    val dataFusionBallistaVersions: Map<DataFusionBallistaComponent, String> = emptyMap()
) :
    Versions<DataFusionBallistaVersion>(
        dataFusionBallistaVersions.mapKeys { it.key as Component<DataFusionBallistaVersion> }
    ) {
    companion object :
        VersionsFetcher<DataFusionBallistaVersion> by DataFusionBallistaVersionFetcher {
        fun String.toDataFusionBallistaVersion(): DataFusionBallistaVersion =
            DataFusionBallistaVersion(mapOf(DataFusionBallistaComponent.DataFusionBallista to this))
    }
}
