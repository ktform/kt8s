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
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.ExternalDnsVersion

object ExternalDnsVersionFetcher : VersionsFetcher<ExternalDnsVersion> {
    override suspend fun getVersions(last: Int): Map<Component<ExternalDnsVersion>, List<String>> =
        ExternalDnsComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<ExternalDnsVersion>): Option<String> =
        when (component) {
            is ExternalDnsComponent if component == ExternalDnsComponent.ExternalDns ->
                "https://github.com/kubernetes-sigs/external-dns".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<ExternalDnsVersion>): Option<String> =
        when (component) {
            is ExternalDnsComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<ExternalDnsVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is ExternalDnsComponent -> listOf("0.18.0", "0.17.0", "0.16.1")
            else -> emptyList()
        }
}
