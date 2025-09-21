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
import dev.ktform.kt8s.container.components.ArgoComponent
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.ArgoVersion

object ArgoVersionFetcher : VersionsFetcher<ArgoVersion> {
    override suspend fun getVersions(last: Int): Map<Component<ArgoVersion>, List<String>> =
        ArgoComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<ArgoVersion>): Option<String> =
        when (component) {
            is ArgoComponent if component == ArgoComponent.ArgoCd ->
                "https://github.com/argoproj/argo-cd".some()

            is ArgoComponent if component == ArgoComponent.ArgoWorkflows ->
                "https://github.com/argoproj/argo-workflows".some()

            is ArgoComponent if component == ArgoComponent.ArgoEvents ->
                "https://github.com/argoproj/argo-events".some()

            is ArgoComponent if component == ArgoComponent.ArgoRollouts ->
                "https://github.com/argoproj/argo-rollouts".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<ArgoVersion>): Option<String> =
        when (component) {
            is ArgoComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<ArgoVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is ArgoComponent if this == ArgoComponent.ArgoCd -> listOf("3.0.12", "3.0.11", "3.0.10")

            is ArgoComponent if this == ArgoComponent.ArgoWorkflows -> listOf("3.7.0", "3.6.10")

            is ArgoComponent if this == ArgoComponent.ArgoEvents -> listOf("3.7.0", "3.6.10")

            is ArgoComponent if this == ArgoComponent.ArgoRollouts ->
                listOf("1.8.3", "1.8.2", "1.8.1")

            else -> emptyList()
        }
}
