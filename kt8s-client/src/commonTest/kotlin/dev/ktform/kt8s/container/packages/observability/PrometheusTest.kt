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
import dev.ktform.kt8s.container.components.PrometheusComponent
import dev.ktform.kt8s.container.fetchers.PrometheusVersionFetcher
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class PrometheusTest {

    @Test
    fun testPrometheus() {
        runTest(timeout = 10.seconds) {
            assertThat(PrometheusVersionFetcher.getLatestVersions()).isNotEmpty()

            PrometheusVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val prometheus =
                    when (component) {
                        is PrometheusComponent if (component == PrometheusComponent.Prometheus) ->
                            Prometheus(version)

                        else -> throw Exception("Unknown component: $component")
                    }

                prometheus
                    .render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
