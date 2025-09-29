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
import dev.ktform.kt8s.container.components.OpenBaoComponent
import dev.ktform.kt8s.container.components.OpenTofuComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.OpenBaoVersion

object OpenBaoVersionFetcher : VersionsFetcher<OpenBaoVersion> {
    override suspend fun getVersions(last: Int): Map<Component<OpenBaoVersion>, List<String>> =
        OpenBaoComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<OpenBaoVersion>): Option<String> =
        when (component) {
            is OpenBaoComponent if component == OpenBaoComponent.OpenBao ->
                "https://github.com/openbao/openbao".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<OpenBaoVersion>): Option<String> =
        when (component) {
            is OpenTofuComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<OpenBaoVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is OpenBaoComponent if this == OpenBaoComponent.OpenBao ->
                listOf("2.4.1", "2.4.0", "2.3.2", "2.3.1", "2.3.0")

            else -> emptyList()
        }
}
