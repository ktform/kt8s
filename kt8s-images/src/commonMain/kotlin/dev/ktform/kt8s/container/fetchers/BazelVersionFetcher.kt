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
import dev.ktform.kt8s.container.components.BazelComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.BazelVersion

object BazelVersionFetcher : VersionsFetcher<BazelVersion> {
    override suspend fun getVersions(last: Int): Map<Component<BazelVersion>, List<String>> =
        BazelComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<BazelVersion>): Option<String> =
        when (component) {
            is BazelComponent if component == BazelComponent.Bazel ->
                "https://github.com/bazelbuild/bazel".some()

            is BazelComponent if component == BazelComponent.Bazelisk ->
                "https://github.com/bazelbuild/bazelisk".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<BazelVersion>) =
        when (component) {
            is BazelComponent if component == BazelComponent.Bazel -> this.withVPrefix().some()
            is BazelComponent if component == BazelComponent.Bazelisk -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<BazelVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is BazelComponent if this == BazelComponent.Bazel -> listOf("8.3.1", "8.3.0")
            is BazelComponent if this == BazelComponent.Bazelisk ->
                listOf("1.27.0", "1.26.0", "1.25.0")
            else -> emptyList()
        }
}
