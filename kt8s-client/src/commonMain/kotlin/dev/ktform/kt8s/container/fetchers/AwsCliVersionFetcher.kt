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
import arrow.core.getOrElse
import arrow.core.some
import dev.ktform.kt8s.container.components.AwsCliComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.AwsCliVersion

object AwsCliVersionFetcher : VersionsFetcher<AwsCliVersion> {
    override suspend fun getVersions(last: Int): Map<Component<AwsCliVersion>, List<String>> =
        AwsCliComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<AwsCliVersion>) =
        when (component) {
            is AwsCliComponent -> "https://github.com/aws/aws-cli".some()
            else -> None
        }

    override fun String.toRepoVersion(component: Component<AwsCliVersion>) =
        when (component) {
            is AwsCliComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<AwsCliVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is AwsCliComponent -> listOf("2.31.3", "2.31.2", "2.31.1", "2.31.0", "2.30.7")
            else -> emptyList()
        }
}
