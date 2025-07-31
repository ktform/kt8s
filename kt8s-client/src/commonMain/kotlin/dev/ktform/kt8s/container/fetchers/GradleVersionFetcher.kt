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
import dev.ktform.kt8s.container.components.GradleComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.GradleVersion

object GradleVersionFetcher : VersionsFetcher<GradleVersion> {
    override suspend fun getVersions(last: Int): Map<Component<GradleVersion>, List<String>> =
        GradleComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<GradleVersion>): Option<String> =
        when (component) {
            is GradleComponent if component == GradleComponent.Gradle ->
                "https://github.com/gradle/gradle".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<GradleVersion>): Option<String> =
        when (component) {
            is GradleComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<GradleVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is GradleComponent -> listOf("9.0.0", "8.14.3")
            else -> emptyList()
        }
}
