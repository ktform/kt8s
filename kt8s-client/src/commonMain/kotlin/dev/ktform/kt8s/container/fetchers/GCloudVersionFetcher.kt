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

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.GCloudComponent
import dev.ktform.kt8s.container.gcs.GcsClient
import dev.ktform.kt8s.container.versions.GCloudVersion
import io.github.z4kn4fein.semver.toVersion

object GCloudVersionFetcher : VersionsFetcher<GCloudVersion> {
    private const val BUCKET = "cloud-sdk-release"

    private const val GCLOUD_RELEASE_PREFIX = "google-cloud-cli-"

    override suspend fun getVersions(last: Int): Map<Component<GCloudVersion>, List<String>> {
        val versions =
            GcsClient()
                .getVersions(BUCKET)
                .getOrElse { emptyList() }
                .filter { it.contains("-linux-") }
                .map { it.removePrefix(GCLOUD_RELEASE_PREFIX).substringBefore("-linux-") }
                .mapNotNull { s -> Either.catch { s.toVersion() }.getOrNull() }
                .filter { v -> !v.isPreRelease && (v.major == 0 || v.isStable) }
                .sortedDescending()
                .distinct()
                .map { it.toString() }

        return mapOf(GCloudComponent.GCloud to versions.take(last))
    }

    override fun repo(component: Component<GCloudVersion>): Option<String> =
        when (component) {
            is GCloudComponent if component == GCloudComponent.GCloud ->
                "https://console.cloud.google.com/storage/browser/cloud-sdk-release".some()

            else -> None
        }

    override fun String.toRepoVersion(component: Component<GCloudVersion>): Option<String> =
        when (component) {
            is GCloudComponent -> (GCLOUD_RELEASE_PREFIX + this).some()
            else -> None
        }

    override fun Component<GCloudVersion>.knownLatestVersions(): List<String> =
        when (this) {
            is GCloudComponent -> listOf("540.0.0", "539.0.0", "538.0.0", "537.0.0", "536.0.1")
            else -> emptyList()
        }
}
