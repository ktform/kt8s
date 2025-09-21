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
package dev.ktform.kt8s.container.packages.compute

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.KarpenterComponent
import dev.ktform.kt8s.container.fetchers.KarpenterVersionFetcher
import dev.ktform.kt8s.container.packages.compute.karpenter.KarpenterAWS
import dev.ktform.kt8s.container.packages.compute.karpenter.KarpenterAzure
import dev.ktform.kt8s.container.packages.compute.karpenter.KarpenterGCP
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class KarpenterTest {

    @Test
    fun testKarpenter() {
        runTest(timeout = 10.seconds) {
            assertThat(KarpenterVersionFetcher.getLatestVersions()).isNotEmpty()

            KarpenterVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val karpenter =
                    when (component) {
                        is KarpenterComponent if (component == KarpenterComponent.Karpenter) ->
                            Karpenter(version)

                        is KarpenterComponent if (component == KarpenterComponent.AWS) ->
                            KarpenterAWS(version)

                        is KarpenterComponent if (component == KarpenterComponent.Azure) ->
                            KarpenterAzure(version)

                        is KarpenterComponent if (component == KarpenterComponent.GCP) ->
                            KarpenterGCP(version)

                        else -> throw Exception("Unknown component: $component")
                    }

                karpenter
                    .render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
