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
import dev.ktform.kt8s.container.components.JavaComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.versions.JavaVersion

object JavaVersionFetcher : VersionsFetcher<JavaVersion> {
    override suspend fun getVersions(last: Int): Map<Component<JavaVersion>, List<String>> =
        JavaComponent.entries.associateWith {
            val repo =
                repo(it).getOrElse {
                    return@associateWith emptyList()
                }

            when (it) {
                JavaComponent.OpenJDK ->
                    githubVersions(repo, "", asIs = true).getOrElse { emptyList() }
                JavaComponent.OpenJ9 -> githubVersions(repo, "openj9-").getOrElse { emptyList() }
                JavaComponent.GraalVM -> githubVersions(repo, "vm-ce-").getOrElse { emptyList() }
                else -> emptyList()
            }
        }

    override fun repo(component: Component<JavaVersion>): Option<String> =
        when (component) {
            is JavaComponent if component == JavaComponent.OpenJDK ->
                "https://github.com/openjdk/jdk".some()

            is JavaComponent if component == JavaComponent.OpenJ9 ->
                "https://github.com/eclipse-openj9/openj9".some()

            is JavaComponent if component == JavaComponent.GraalVM ->
                "https://github.com/oracle/graal".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<JavaVersion>): Option<String> =
        when (component) {
            is JavaComponent if component == JavaComponent.OpenJDK -> this.some()
            is JavaComponent if component == JavaComponent.OpenJ9 -> "openj9-$this".some()
            is JavaComponent if component == JavaComponent.GraalVM -> "vm-ce-$this".some()
            else -> None
        }

    override fun Component<JavaVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is JavaComponent if this == JavaComponent.OpenJDK -> listOf()
            is JavaComponent if this == JavaComponent.OpenJ9 -> listOf()
            is JavaComponent if this == JavaComponent.GraalVM -> listOf()
            else -> emptyList()
        }
}
