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
package dev.ktform.kt8s.container.fetchers

import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.RustComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.RustVersion

object RustVersionFetcher : VersionsFetcher<RustVersion> {
    override suspend fun getVersions(last: Int): Map<Component<RustVersion>, List<String>> =
        RustComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<RustVersion>): Option<String> =
        when (component) {
            is RustComponent if
                (component == RustComponent.Stable || component == RustComponent.Nightly)
             -> "https://github.com/rust-lang/rust".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<RustVersion>): Option<String> =
        when (component) {
            is RustComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<RustVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is RustComponent -> listOf("1.90.0", "1.89.0", "1.88.0", "1.87.0", "1.86.0")
            else -> emptyList()
        }
}
