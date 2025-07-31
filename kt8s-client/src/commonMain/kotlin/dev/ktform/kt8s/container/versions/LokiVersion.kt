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
import dev.ktform.kt8s.container.components.LokiComponent
import dev.ktform.kt8s.container.fetchers.LokiVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class LokiVersion(val lokiVersions: Map<LokiComponent, String> = emptyMap()) :
    Versions<LokiVersion>(lokiVersions.mapKeys { it.key as Component<LokiVersion> }) {
    companion object : VersionsFetcher<LokiVersion> by LokiVersionFetcher {
        fun String.toLokiVersion(): LokiVersion = LokiVersion(mapOf(LokiComponent.Loki to this))
    }
}
