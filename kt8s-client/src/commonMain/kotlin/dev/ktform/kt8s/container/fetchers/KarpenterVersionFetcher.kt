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
import dev.ktform.kt8s.container.components.KarpenterComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.withVPrefix
import dev.ktform.kt8s.container.versions.KarpenterVersion

object KarpenterVersionFetcher : VersionsFetcher<KarpenterVersion> {
    override suspend fun getVersions(last: Int): Map<Component<KarpenterVersion>, List<String>> =
        KarpenterComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<KarpenterVersion>): Option<String> =
        when (component) {
            is KarpenterComponent if component == KarpenterComponent.Karpenter ->
                "https://github.com/kubernetes-sigs/karpenter".some()

            is KarpenterComponent if component == KarpenterComponent.AWS ->
                "https://github.com/kubernetes-sigs/karpenter".some()

            is KarpenterComponent if component == KarpenterComponent.Azure ->
                "https://github.com/kubernetes-sigs/karpenter".some()

            is KarpenterComponent if component == KarpenterComponent.GCP ->
                "https://github.com/kubernetes-sigs/karpenter".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<KarpenterVersion>): Option<String> =
        when (component) {
            is KarpenterComponent -> this.withVPrefix().some()
            else -> None
        }

    override fun Component<KarpenterVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is KarpenterComponent if this == KarpenterComponent.Karpenter -> listOf()
            is KarpenterComponent if this == KarpenterComponent.AWS -> listOf()
            is KarpenterComponent if this == KarpenterComponent.Azure -> listOf()
            is KarpenterComponent if this == KarpenterComponent.GCP -> listOf()

            else -> emptyList()
        }
}
