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
    const val PYPY_PYTHON_VERSION = "3.11"

    private const val PYPY_RELEASE_PREFIX = "release-pypy${PYPY_PYTHON_VERSION}-v"

    override suspend fun getVersions(last: Int): Map<Component<PythonVersion>, List<String>> =
        PythonComponent.entries.associateWith { component ->
            when (component) {
                is PythonComponent if component == PythonComponent.PyPy -> {
                    repo(component).fold({ emptyList() }) { repo ->
                        githubVersions(repo, PYPY_RELEASE_PREFIX, limit = last).getOrElse {
                            emptyList()
                        }
                    }
                }

                else ->
                    repo(component).fold({ emptyList() }) { repo ->
                        githubVersions(repo, limit = last).getOrElse { emptyList() }
                    }
            }
        }

    override fun repo(component: Component<PythonVersion>): Option<String> =
        when (component) {
            is PythonComponent if component == PythonComponent.CPython ->
                "https://github.com/python/cpython".some()

            is PythonComponent if component == PythonComponent.PyPy ->
                "https://github.com/pypy/pypy".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<PythonVersion>): Option<String> =
        when (component) {
            is PythonComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<PythonVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is PythonComponent if this == PythonComponent.CPython ->
                listOf("3.13.7", "3.13.6", "3.13.5", "3.13.4", "3.13.3")

            is PythonComponent if this == PythonComponent.PyPy ->
                listOf("7.3.20", "7.3.19", "7.3.18")

            else -> emptyList()
        }
}
