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

import com.varabyte.truthish.assertWithMessage
import dev.ktform.kt8s.container.fetchers.GradleVersionFetcher.knownLatestVersions
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class GradleVersionFetcherTest {

    @Test
    fun testGradleVersionFetcherLatest() {
        runTest(timeout = 10.seconds) {
            GradleVersionFetcher.getVersions().forEach { (component, versions) ->
                assertWithMessage("${component.name} versions")
                    .that(versions)
                    .containsExactly(component.knownLatestVersions())
            }
        }
    }
}
