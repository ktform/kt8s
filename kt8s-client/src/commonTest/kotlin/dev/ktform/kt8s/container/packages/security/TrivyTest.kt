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
package dev.ktform.kt8s.container.packages.security

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.TrivyComponent
import dev.ktform.kt8s.container.fetchers.TrivyVersionFetcher
import dev.ktform.kt8s.container.packages.Trivy
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class TrivyTest {

    @Test
    fun testTrivy() {
        runTest(timeout = 10.seconds) {
            assertThat(TrivyVersionFetcher.getLatestVersions()).isNotEmpty()

            TrivyVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val trivy =
                    when (component) {
                        is TrivyComponent if (component == TrivyComponent.Trivy) -> Trivy(version)

                        else -> throw Exception("Unknown component: $component")
                    }

                trivy
                    .render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
