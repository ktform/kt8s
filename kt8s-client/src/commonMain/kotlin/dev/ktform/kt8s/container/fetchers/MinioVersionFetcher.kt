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
import dev.ktform.kt8s.container.components.MinioComponent
import dev.ktform.kt8s.container.fetchers.VersionsFetcher.Companion.githubVersions
import dev.ktform.kt8s.container.versions.MinioVersion

object MinioVersionFetcher : VersionsFetcher<MinioVersion> {
    const val VERSION_PREFIX = "RELEASE."

    override suspend fun getVersions(last: Int): Map<Component<MinioVersion>, List<String>> =
        MinioComponent.entries.associateWith {
            repo(it).fold({ emptyList() }) { repo ->
                githubVersions(repo).getOrElse { emptyList() }
            }
        }

    override fun repo(component: Component<MinioVersion>): Option<String> =
        when (component) {
            is MinioComponent if component == MinioComponent.Minio ->
                "https://github.com/minio/minio".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<MinioVersion>): Option<String> =
        when (component) {
            is MinioComponent -> "$VERSION_PREFIX$this".some()
            else -> None
        }

    override fun Component<MinioVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is MinioComponent -> listOf("2025-07-23T15-54-02Z", "2025-07-18T21-56-31Z")
            else -> emptyList()
        }
}
