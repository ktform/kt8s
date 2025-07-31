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
import dev.ktform.kt8s.container.components.DeschedulerComponent
import dev.ktform.kt8s.container.fetchers.DeschedulerVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class DeschedulerVersion(val deschedulerVersions: Map<DeschedulerComponent, String> = emptyMap()) :
    Versions<DeschedulerVersion>(
        deschedulerVersions.mapKeys { it.key as Component<DeschedulerVersion> }
    ) {
    companion object : VersionsFetcher<DeschedulerVersion> by DeschedulerVersionFetcher {
        fun String.toDeschedulerVersion(): DeschedulerVersion =
            DeschedulerVersion(mapOf(DeschedulerComponent.Descheduler to this))
    }
}
