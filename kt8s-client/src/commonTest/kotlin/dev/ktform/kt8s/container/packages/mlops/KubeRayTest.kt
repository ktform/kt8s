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
package dev.ktform.kt8s.container.packages.mlops

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.KubeRayComponent
import dev.ktform.kt8s.container.fetchers.KubeRayVersionFetcher
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class KubeRayTest {

    @Test
    fun testKubeRay() {
        runTest(timeout = 10.seconds) {
            assertThat(KubeRayVersionFetcher.getLatestVersions()).isNotEmpty()

            KubeRayVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val cli =
                    when (component) {
                        is KubeRayComponent if (component == KubeRayComponent.KubeRay) ->
                            KubeRay(version)

                        else -> throw Exception("Unknown component: $component")
                    }

                cli.render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
