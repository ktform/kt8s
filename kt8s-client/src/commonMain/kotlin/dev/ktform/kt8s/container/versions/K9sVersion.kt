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
import dev.ktform.kt8s.container.components.K9sComponent
import dev.ktform.kt8s.container.fetchers.K9sVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class K9sVersion(val k9sVersions: Map<K9sComponent, String> = emptyMap()) :
    Versions<K9sVersion>(k9sVersions.mapKeys { it.key as Component<K9sVersion> }) {
    companion object : VersionsFetcher<K9sVersion> by K9sVersionFetcher {
        fun String.toK9sVersion(): K9sVersion = K9sVersion(mapOf(K9sComponent.K9s to this))
    }
}
