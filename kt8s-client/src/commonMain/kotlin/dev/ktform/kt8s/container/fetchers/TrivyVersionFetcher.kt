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
import dev.ktform.kt8s.container.components.TrivyComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.TrivyVersion

object TrivyVersionFetcher : VersionsFetcher<TrivyVersion> {
    override suspend fun getVersions(last: Int): Map<Component<TrivyVersion>, List<String>> =
        TrivyComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<TrivyVersion>): Option<String> =
        when (component) {
            is TrivyComponent if component == TrivyComponent.Trivy ->
                "https://github.com/aquasecurity/trivy".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<TrivyVersion>): Option<String> =
        when (component) {
            is TrivyComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<TrivyVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is TrivyComponent -> listOf("0.66.0", "0.65.0", "0.64.1", "0.64.0", "0.63.0")

            else -> emptyList()
        }
}
