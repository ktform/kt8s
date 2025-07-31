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
import dev.ktform.kt8s.container.components.ScalaComponent
import dev.ktform.kt8s.container.fetchers.ScalaVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class ScalaVersion(val scalaVersions: Map<ScalaComponent, String> = emptyMap()) :
    Versions<ScalaVersion>(scalaVersions.mapKeys { it.key as Component<ScalaVersion> }) {
    fun plus(other: ScalaVersion): ScalaVersion = ScalaVersion(scalaVersions + other.scalaVersions)

    companion object : VersionsFetcher<ScalaVersion> by ScalaVersionFetcher {
        fun String.toScalaVersion(): ScalaVersion =
            ScalaVersion(mapOf(ScalaComponent.Scala to this))

        fun String.toSbtVersion(): ScalaVersion = ScalaVersion(mapOf(ScalaComponent.Sbt to this))
    }
}
