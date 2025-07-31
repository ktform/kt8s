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
import dev.ktform.kt8s.container.components.KarpenterComponent
import dev.ktform.kt8s.container.fetchers.KarpenterVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class KarpenterVersion(val karpenterVersions: Map<KarpenterComponent, String> = emptyMap()) :
    Versions<KarpenterVersion>(
        karpenterVersions.mapKeys { it.key as Component<KarpenterVersion> }
    ) {
    fun plus(other: KarpenterVersion): KarpenterVersion =
        KarpenterVersion(karpenterVersions + other.karpenterVersions)

    companion object : VersionsFetcher<KarpenterVersion> by KarpenterVersionFetcher {
        fun String.toVersion(component: KarpenterComponent): KarpenterVersion =
            KarpenterVersion(mapOf(component to this))
    }
}
