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
import dev.ktform.kt8s.container.components.ScyllaDBComponent
import dev.ktform.kt8s.container.fetchers.ScyllaDBVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class ScyllaDBVersion(val scyllaDBVersions: Map<ScyllaDBComponent, String> = emptyMap()) :
    Versions<ScyllaDBVersion>(scyllaDBVersions.mapKeys { it.key as Component<ScyllaDBVersion> }) {
    companion object : VersionsFetcher<ScyllaDBVersion> by ScyllaDBVersionFetcher {
        fun String.toScyllaDBVersion(): ScyllaDBVersion =
            ScyllaDBVersion(mapOf(ScyllaDBComponent.ScyllaDB to this))
    }
}
