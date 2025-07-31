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
import dev.ktform.kt8s.container.components.PipXComponent
import dev.ktform.kt8s.container.fetchers.PipXVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class PipXVersion(val pipXVersions: Map<PipXComponent, String> = emptyMap()) :
    Versions<PipXVersion>(pipXVersions.mapKeys { it.key as Component<PipXVersion> }) {
    companion object : VersionsFetcher<PipXVersion> by PipXVersionFetcher {
        fun String.toPipXVersion(): PipXVersion = PipXVersion(mapOf(PipXComponent.PipX to this))
    }
}
