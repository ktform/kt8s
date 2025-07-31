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
import dev.ktform.kt8s.container.components.MinikubeComponent
import dev.ktform.kt8s.container.fetchers.MinikubeVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class MinikubeVersion(val minikubeVersions: Map<MinikubeComponent, String> = emptyMap()) :
    Versions<MinikubeVersion>(minikubeVersions.mapKeys { it.key as Component<MinikubeVersion> }) {
    companion object : VersionsFetcher<MinikubeVersion> by MinikubeVersionFetcher {
        fun String.toMinikubeVersion(): MinikubeVersion =
            MinikubeVersion(mapOf(MinikubeComponent.Minikube to this))
    }
}
