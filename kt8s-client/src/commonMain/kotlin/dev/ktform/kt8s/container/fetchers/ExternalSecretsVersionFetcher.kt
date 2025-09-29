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
import dev.ktform.kt8s.container.components.ExternalDnsComponent
import dev.ktform.kt8s.container.components.ExternalSecretsComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.ExternalSecretsVersion

object ExternalSecretsVersionFetcher : VersionsFetcher<ExternalSecretsVersion> {
    override suspend fun getVersions(
        last: Int
    ): Map<Component<ExternalSecretsVersion>, List<String>> =
        ExternalSecretsComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo, limit = last).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<ExternalSecretsVersion>): Option<String> =
        when (component) {
            is ExternalSecretsComponent if component == ExternalSecretsComponent.ExternalSecrets ->
                "https://github.com/external-secrets/external-secrets".some()

            else -> None
        }

    override fun String.toRepoVersion(
        component: Component<ExternalSecretsVersion>
    ): Option<String> =
        when (component) {
            is ExternalSecretsComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<ExternalSecretsVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is ExternalDnsComponent -> listOf("0.20.1", "0.20.0", "0.19.2", "0.19.1", "0.19.0")
            else -> emptyList()
        }
}
