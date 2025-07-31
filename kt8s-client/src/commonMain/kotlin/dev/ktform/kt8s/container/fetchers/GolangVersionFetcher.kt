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
import dev.ktform.kt8s.container.components.GolangComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.versions.GolangVersion

object GolangVersionFetcher : VersionsFetcher<GolangVersion> {
    override suspend fun getVersions(last: Int): Map<Component<GolangVersion>, List<String>> {
        val repo = repo(GolangComponent.Golang).getOrElse { "" }
        return githubVersions(repo, "go")
            .map { versions ->
                mapOf(GolangComponent.Golang as Component<GolangVersion> to versions)
            }
            .getOrElse { emptyMap() }
    }

    override fun repo(component: Component<GolangVersion>): Option<String> =
        when (component) {
            is GolangComponent -> "https://github.com/golang/go".some()
            else -> None
        }

    override fun String.toRepoVersion(component: Component<GolangVersion>): Option<String> =
        when (component) {
            is GolangComponent -> "go$this".some()
            else -> None
        }

    override fun Component<GolangVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is GolangComponent -> listOf("1.24.6", "1.24.5")
            else -> emptyList()
        }
}
