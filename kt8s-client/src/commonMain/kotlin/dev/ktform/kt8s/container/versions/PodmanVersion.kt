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
import dev.ktform.kt8s.container.components.PodmanComponent
import dev.ktform.kt8s.container.fetchers.PodmanVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class PodmanVersion(val podmanVersions: Map<PodmanComponent, String> = emptyMap()) :
    Versions<PodmanVersion>(podmanVersions.mapKeys { it.key as Component<PodmanVersion> }) {
    companion object : VersionsFetcher<PodmanVersion> by PodmanVersionFetcher {
        fun String.toPodmanVersion(): PodmanVersion =
            PodmanVersion(mapOf(PodmanComponent.Podman to this))
    }
}
