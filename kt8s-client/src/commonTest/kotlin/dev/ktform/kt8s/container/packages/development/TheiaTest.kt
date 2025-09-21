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
package dev.ktform.kt8s.container.packages.development

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.TheiaComponent
import dev.ktform.kt8s.container.fetchers.TheiaVersionFetcher
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class TheiaTest {

    @Test
    fun testTheia() {
        runTest(timeout = 10.seconds) {
            assertThat(TheiaVersionFetcher.getLatestVersions()).isNotEmpty()

            TheiaVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val theia =
                    when (component) {
                        is TheiaComponent if (component == TheiaComponent.Theia) -> Theia(version)

                        else -> throw Exception("Unknown component: $component")
                    }

                theia
                    .render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
