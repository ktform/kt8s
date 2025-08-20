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
import dev.ktform.kt8s.container.components.LocalPathProvisionerComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.LocalPathProvisionerVersion

object LocalPathProvisionerVersionFetcher : VersionsFetcher<LocalPathProvisionerVersion> {
    override suspend fun getVersions(
        last: Int
    ): Map<Component<LocalPathProvisionerVersion>, List<String>> =
        LocalPathProvisionerComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<LocalPathProvisionerVersion>): Option<String> =
        when (component) {
            is LocalPathProvisionerComponent if
                component == LocalPathProvisionerComponent.LocalPathProvisioner
             -> "https://github.com/rancher/local-path-provisioner".some()

            else -> None
        }

    override fun String.toRepoVersion(
        component: Component<LocalPathProvisionerVersion>
    ): Option<String> =
        when (component) {
            is LocalPathProvisionerComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<LocalPathProvisionerVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is LocalPathProvisionerComponent -> listOf("0.0.32", "0.0.31", "0.0.30")
            else -> emptyList()
        }
}
