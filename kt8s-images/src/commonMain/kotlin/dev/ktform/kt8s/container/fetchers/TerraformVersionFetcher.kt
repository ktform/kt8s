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
import dev.ktform.kt8s.container.components.TerraformComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.TerraformVersion

object TerraformVersionFetcher : VersionsFetcher<TerraformVersion> {
    override suspend fun getVersions(last: Int): Map<Component<TerraformVersion>, List<String>> =
        TerraformComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<TerraformVersion>): Option<String> =
        when (component) {
            is TerraformComponent if component == TerraformComponent.Terraform ->
                "https://github.com/hashicorp/terraform".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<TerraformVersion>): Option<String> =
        when (component) {
            is TerraformComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<TerraformVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is TerraformComponent -> listOf("1.12.2", "1.12.1")
            else -> emptyList()
        }
}
