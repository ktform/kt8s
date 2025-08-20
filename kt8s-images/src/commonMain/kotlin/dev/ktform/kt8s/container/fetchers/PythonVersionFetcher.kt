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
import dev.ktform.kt8s.container.components.PythonComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.PythonVersion

object PythonVersionFetcher : VersionsFetcher<PythonVersion> {
    override suspend fun getVersions(last: Int): Map<Component<PythonVersion>, List<String>> =
        PythonComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<PythonVersion>): Option<String> =
        when (component) {
            is PythonComponent if component == PythonComponent.CPython ->
                "https://github.com/python/cpython".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<PythonVersion>): Option<String> =
        when (component) {
            is PythonComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<PythonVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is PythonComponent -> listOf("3.13.6", "3.13.5", "3.13.4")
            else -> emptyList()
        }
}
