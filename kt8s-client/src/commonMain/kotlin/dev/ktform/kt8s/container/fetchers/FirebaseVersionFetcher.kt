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
import dev.ktform.kt8s.container.components.FirebaseComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.FirebaseVersion

object FirebaseVersionFetcher : VersionsFetcher<FirebaseVersion> {
    override suspend fun getVersions(last: Int): Map<Component<FirebaseVersion>, List<String>> =
        FirebaseComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<FirebaseVersion>): Option<String> =
        when (component) {
            is FirebaseComponent if component == FirebaseComponent.Firebase ->
                "https://github.com/firebase/firebase-tools".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<FirebaseVersion>): Option<String> =
        when (component) {
            is FirebaseComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<FirebaseVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is FirebaseComponent -> listOf("14.17.0", "14.16.0", "14.15.2", "14.15.1", "14.15.0")
            else -> emptyList()
        }
}
