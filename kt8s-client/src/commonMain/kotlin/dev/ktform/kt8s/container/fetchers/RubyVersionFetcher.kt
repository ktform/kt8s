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
import dev.ktform.kt8s.container.components.RubyComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.RubyVersion

object RubyVersionFetcher : VersionsFetcher<RubyVersion> {
    override suspend fun getVersions(last: Int): Map<Component<RubyVersion>, List<String>> =
        RubyComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }.map { v -> v.replace("_", ".") }
            }
        }

    override fun repo(component: Component<RubyVersion>): Option<String> =
        when (component) {
            is RubyComponent if component == RubyComponent.Ruby ->
                "https://github.com/ruby/ruby".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<RubyVersion>): Option<String> =
        when (component) {
            is RubyComponent -> this.withVPrefix().replace(".", "_").some()
            else -> None
        }

    override fun Component<RubyVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is RubyComponent -> listOf("3.4.5", "3.4.4", "3.4.3")
            else -> emptyList()
        }
}
