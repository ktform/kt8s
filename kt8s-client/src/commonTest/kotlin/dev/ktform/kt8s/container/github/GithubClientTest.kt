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
package dev.ktform.kt8s.dev.ktform.kt8s.container.github

import com.varabyte.truthish.assertThat
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class GithubClientTest {

    private val githubClient = GithubClient()

    @Test
    fun testGetOwnerRepoFromSshUrl() {
        val result = githubClient.getOwnerRepoFrom("git@github.com:astral-sh/uv.git")
        assertThat(result).isEqualTo("astral-sh" to "uv")
    }

    @Test
    fun testGetOwnerRepoFromHttpsUrl() {
        val result = githubClient.getOwnerRepoFrom("https://github.com/astral-sh/uv.git")
        assertThat(result).isEqualTo("astral-sh" to "uv")
    }

    @Test
    fun testGetOwnerRepoFromHttpsUrlWithoutGitSuffix() {
        val result = githubClient.getOwnerRepoFrom("https://github.com/astral-sh/uv")
        assertThat(result).isEqualTo("astral-sh" to "uv")
    }

    @Test
    fun testGetOwnerRepoFromInvalidUrl() {
        val result = githubClient.getOwnerRepoFrom("invalid-url")
        assertThat(result.first).isEqualTo("")
        assertThat(result.second).isEqualTo("")
    }

    @Test
    fun testGitTags() = runTest {
        githubClient.getTags("https://github.com/astral-sh/uv.git").let {
            it.fold({ err -> assertThat(err).isEmpty() }, { tags -> assertThat(tags).isNotEmpty() })
        }
    }

    @Test
    fun testGitRelease() =
        runTest(timeout = 30.seconds) {
            githubClient.getReleases("https://github.com/astral-sh/uv.git").let {
                it.fold(
                    { err -> assertThat(err).isEmpty() },
                    { releases -> assertThat(releases).isNotEmpty() },
                )
            }
        }
}
