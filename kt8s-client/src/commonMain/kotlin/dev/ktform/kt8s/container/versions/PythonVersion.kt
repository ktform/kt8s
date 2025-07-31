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
import dev.ktform.kt8s.container.components.PythonComponent
import dev.ktform.kt8s.container.fetchers.PythonVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class PythonVersion(val pythonVersions: Map<PythonComponent, String> = emptyMap()) :
    Versions<PythonVersion>(pythonVersions.mapKeys { it.key as Component<PythonVersion> }) {
    fun plus(other: PythonVersion): PythonVersion =
        PythonVersion(pythonVersions + other.pythonVersions)

    companion object : VersionsFetcher<PythonVersion> by PythonVersionFetcher {
        fun String.toPythonVersion(): PythonVersion =
            PythonVersion(mapOf(PythonComponent.CPython to this))

        fun String.toPyPyVersion(): PythonVersion =
            PythonVersion(mapOf(PythonComponent.PyPy to this))
    }
}
