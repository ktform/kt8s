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
import dev.ktform.kt8s.container.components.RustComponent
import dev.ktform.kt8s.container.fetchers.RustVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class RustVersion(val rustVersions: Map<RustComponent, String> = emptyMap()) :
    Versions<RustVersion>(rustVersions.mapKeys { it.key as Component<RustVersion> }) {
    fun plus(other: RustVersion): RustVersion = RustVersion(rustVersions + other.rustVersions)

    companion object : VersionsFetcher<RustVersion> by RustVersionFetcher {
        fun String.toRustVersion(): RustVersion = RustVersion(mapOf(RustComponent.Stable to this))

        fun String.toRustNightlyVersion(): RustVersion =
            RustVersion(mapOf(RustComponent.Nightly to this))
    }
}
