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

import dev.ktform.kt8s.container.Distro
import dev.ktform.kt8s.container.components.BaseComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.BaseVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class BaseVersion(val baseVersions: Map<BaseComponent, Map<Distro, String>> = emptyMap()) :
    Versions<BaseVersion>(
        baseVersions
            .mapKeys { (k, _) -> k as Component<BaseVersion> }
            .mapValues { (_, distroVersions) ->
                distroVersions.entries.joinToString(separator = " ") { (distro, version) ->
                    "${distro.name.lowercase()}-$version"
                }
            }
    ) {

    companion object : VersionsFetcher<BaseVersion> by BaseVersionFetcher {
        fun String.toDebianVersion(): BaseVersion =
            BaseVersion(mapOf(BaseComponent.Base to mapOf(Distro.Debian to this)))

        fun String.toAlpineVersion(): BaseVersion =
            BaseVersion(mapOf(BaseComponent.Base to mapOf(Distro.Alpine to this)))

        fun String.toPhotonVersion(): BaseVersion =
            BaseVersion(mapOf(BaseComponent.Base to mapOf(Distro.Photon to this)))

        fun String.toOracleVersion(): BaseVersion =
            BaseVersion(mapOf(BaseComponent.Base to mapOf(Distro.Oracle to this)))

        fun String.toRockyVersion(): BaseVersion =
            BaseVersion(mapOf(BaseComponent.Base to mapOf(Distro.Rocky to this)))

        fun String.toUbiVersion(): BaseVersion =
            BaseVersion(mapOf(BaseComponent.Base to mapOf(Distro.UBI to this)))

        // Generic helper to reduce duplication and allow choosing component
        fun String.toBaseVersion(
            distro: Distro,
            component: BaseComponent = BaseComponent.Base,
        ): BaseVersion = BaseVersion(mapOf(component to mapOf(distro to this)))
    }
}
