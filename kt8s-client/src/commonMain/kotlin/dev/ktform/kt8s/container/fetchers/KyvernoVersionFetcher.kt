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
import dev.ktform.kt8s.container.components.KyvernoComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.KyvernoVersion

object KyvernoVersionFetcher : VersionsFetcher<KyvernoVersion> {
    override suspend fun getVersions(last: Int): Map<Component<KyvernoVersion>, List<String>> =
        KyvernoComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<KyvernoVersion>): Option<String> =
        when (component) {
            is KyvernoComponent if component == KyvernoComponent.Kyverno ->
                "https://github.com/kyverno/kyverno".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<KyvernoVersion>): Option<String> =
        when (component) {
            is KyvernoComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<KyvernoVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is KyvernoComponent -> listOf("1.15.2", "1.15.1", "1.15.0", "1.14.4", "1.14.3")
            else -> emptyList()
        }
}
