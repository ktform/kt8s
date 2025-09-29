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
import dev.ktform.kt8s.container.components.Kt8sComponent
import dev.ktform.kt8s.container.fetchers.Kt8sVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class Kt8sVersion(val kindVersions: Map<Kt8sComponent, String> = emptyMap()) :
    Versions<Kt8sVersion>(kindVersions.mapKeys { it.key as Component<Kt8sVersion> }) {
    companion object : VersionsFetcher<Kt8sVersion> by Kt8sVersionFetcher {
        //    fun String.toKindVersion(): Kt8sVersion = Kt8sVersion(mapOf(Kt8sComponent.Kind to
        // this))
    }
}
