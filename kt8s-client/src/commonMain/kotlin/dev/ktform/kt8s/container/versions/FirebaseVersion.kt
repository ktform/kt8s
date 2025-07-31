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
import dev.ktform.kt8s.container.components.FirebaseComponent
import dev.ktform.kt8s.container.fetchers.FirebaseVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class FirebaseVersion(val firebaseVersions: Map<FirebaseComponent, String> = emptyMap()) :
    Versions<FirebaseVersion>(firebaseVersions.mapKeys { it.key as Component<FirebaseVersion> }) {
    companion object : VersionsFetcher<FirebaseVersion> by FirebaseVersionFetcher {
        fun String.toFirebaseVersion(): FirebaseVersion =
            FirebaseVersion(mapOf(FirebaseComponent.Firebase to this))
    }
}
