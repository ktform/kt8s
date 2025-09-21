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
package dev.ktform.kt8s.container.packages.observability

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.GrafanaComponent
import dev.ktform.kt8s.container.fetchers.GrafanaVersionFetcher
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class GrafanaTest {

    @Test
    fun testGrafana() {
        runTest(timeout = 10.seconds) {
            assertThat(GrafanaVersionFetcher.getLatestVersions()).isNotEmpty()

            GrafanaVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val grafana =
                    when (component) {
                        is GrafanaComponent if (component == GrafanaComponent.Grafana) ->
                            Grafana(version)

                        else -> throw Exception("Unknown component: $component")
                    }

                grafana
                    .render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
