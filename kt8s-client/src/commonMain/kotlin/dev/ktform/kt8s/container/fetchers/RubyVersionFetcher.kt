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

import arrow.core.*
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.RubyComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.RubyVersion
import io.github.z4kn4fein.semver.toVersion

object RubyVersionFetcher : VersionsFetcher<RubyVersion> {
    override suspend fun getVersions(last: Int): Map<Component<RubyVersion>, List<String>> =
        RubyComponent.entries.associateWith { component ->
            when (component) {
                is RubyComponent if component == RubyComponent.Ruby -> {
                    repo(component).fold({ emptyList() }) { repo ->
                        githubVersions(repo, prefix = "v", asIs = true, limit = -1)
                            .getOrElse { emptyList() }
                            .map { v -> v.replace("_", ".") }
                            .mapNotNull { s -> Either.catch { s.toVersion() }.getOrNull() }
                            .filter { v -> !v.isPreRelease && (v.major == 0 || v.isStable) }
                            .sortedDescending()
                            .map { it.toString() }
                            .take(last)
                    }
                }

                else -> {
                    repo(component).fold({ emptyList() }) { repo ->
                        githubVersions(repo, "", limit = last).getOrElse { emptyList() }
                    }
                }
            }
        }

    override fun repo(component: Component<RubyVersion>): Option<String> =
        when (component) {
            is RubyComponent if component == RubyComponent.Ruby ->
                "https://github.com/ruby/ruby".some()

            is RubyComponent if component == RubyComponent.MRuby ->
                "https://github.com/mruby/mruby".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<RubyVersion>): Option<String> =
        when (component) {
            is RubyComponent -> this.withVPrefix().replace(".", "_").some()
            else -> this.some()
        }

    override fun Component<RubyVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is RubyComponent if this == RubyComponent.Ruby ->
                listOf("3.4.6", "3.4.5", "3.4.4", "3.4.3", "3.4.2")

            is RubyComponent if this == RubyComponent.MRuby ->
                listOf("3.4.0", "3.3.0", "3.2.0", "3.1.0", "3.0.0")

            else -> emptyList()
        }
}
