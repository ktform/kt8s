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

import dev.ktform.kt8s.container.components.CiliumComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.CiliumVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class CiliumVersion(val ciliumVersions: Map<CiliumComponent, String> = emptyMap()) :
    Versions<CiliumVersion>(ciliumVersions.mapKeys { it.key as Component<CiliumVersion> }) {
    companion object : VersionsFetcher<CiliumVersion> by CiliumVersionFetcher {
        fun String.toCiliumVersion(): CiliumVersion =
            CiliumVersion(mapOf(CiliumComponent.Cilium to this))
    }
}
