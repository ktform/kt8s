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
import dev.ktform.kt8s.container.components.MinioComponent
import dev.ktform.kt8s.container.fetchers.MinioVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class MinioVersion(val minioVersions: Map<MinioComponent, String> = emptyMap()) :
    Versions<MinioVersion>(minioVersions.mapKeys { it.key as Component<MinioVersion> }) {
    companion object : VersionsFetcher<MinioVersion> by MinioVersionFetcher {
        fun String.toMinioVersion(): MinioVersion =
            MinioVersion(mapOf(MinioComponent.Minio to this))
    }
}
