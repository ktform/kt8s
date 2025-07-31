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
import arrow.core.Option
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.versions.Versions
import io.github.z4kn4fein.semver.toVersion

interface VersionsFetcher<T : Versions<T>> {
  suspend fun getVersions(last: Int = 3): Map<Component<T>, List<String>>

  suspend fun getLatestVersions(last: Int = 3): Map<Component<T>, String> = getVersions(last).mapValues { (_, v) -> v.last() }

  fun repo(component: Component<T>): Option<String>

    fun String.toRepoVersion(component: Component<T>): Option<String>

    fun Component<T>.knownLatestVersions(): List<String>

    companion object {
        suspend fun githubVersions(
            repo: String,
            prefix: String = "v",
            asIs: Boolean = false,
        ): Either<String, List<String>> {
            val client = GithubClient()

            return client.getTags(repo).map { all ->
                val trimmed = all.map { it.removePrefix(prefix).trim() }

                if (asIs) {
                    trimmed.sortedDescending()
                } else {
                    trimmed
                        .mapNotNull { s -> Either.catch { s.toVersion() }.getOrNull() }
                        .filter { v -> !v.isPreRelease && (v.major == 0 || v.isStable) }
                        .sortedDescending()
                        .map { it.toString() }
                }
            }
        }

        fun String.withVPrefix() = "v$this"
    }
}
