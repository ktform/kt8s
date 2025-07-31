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
import dev.ktform.kt8s.container.components.PVCAutoresizerComponent
import dev.ktform.kt8s.container.fetchers.PVCAutoresizerVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class PVCAutoresizerVersion(val pvcAutoresizerVersions: Map<PVCAutoresizerComponent, String> = emptyMap()) :
    Versions<PVCAutoresizerVersion>(
        pvcAutoresizerVersions.mapKeys { it.key as Component<PVCAutoresizerVersion> }
    ) {
    companion object : VersionsFetcher<PVCAutoresizerVersion> by PVCAutoresizerVersionFetcher {
        fun String.toPVCAutoresizerVersion(): PVCAutoresizerVersion =
            PVCAutoresizerVersion(mapOf(PVCAutoresizerComponent.PVCAutoresizer to this))
    }
}
