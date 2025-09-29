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
import dev.ktform.kt8s.container.components.ScalaComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.ScalaVersion

object ScalaVersionFetcher : VersionsFetcher<ScalaVersion> {
    override suspend fun getVersions(last: Int): Map<Component<ScalaVersion>, List<String>> =
        ScalaComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<ScalaVersion>): Option<String> =
        when (component) {
            is ScalaComponent if component == ScalaComponent.Scala3 ->
                "https://github.com/scala/scala3".some()

            is ScalaComponent if component == ScalaComponent.Scala ->
                "https://github.com/scala/scala".some()

            is ScalaComponent if component == ScalaComponent.Sbt ->
                "https://github.com/sbt/sbt".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<ScalaVersion>): Option<String> =
        when (component) {
            is ScalaComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<ScalaVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is ScalaComponent if this == ScalaComponent.Scala3 ->
                listOf("3.7.3", "3.7.2", "3.7.1", "3.7.0", "3.6.4")

            is ScalaComponent if this == ScalaComponent.Scala ->
                listOf("2.13.16", "2.13.15", "2.13.14", "2.13.13", "2.13.12")

            is ScalaComponent if this == ScalaComponent.Sbt ->
                listOf("1.11.6", "1.11.5", "1.11.4", "1.11.3", "1.11.2")

            else -> emptyList()
        }
}
