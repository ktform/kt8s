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
import dev.ktform.kt8s.container.components.VPAComponent
import dev.ktform.kt8s.container.fetchers.VpaVersionFetcher
import dev.ktform.kt8s.container.versions.VpaVersion.Companion.toVpaAdmissionControllerVersion
import dev.ktform.kt8s.container.versions.VpaVersion.Companion.toVpaRecommenderVersion
import dev.ktform.kt8s.container.versions.VpaVersion.Companion.toVpaUpdaterVersion
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class VpaTest {

    @Test
    fun testVpa() {
        runTest(timeout = 10.seconds) {
            assertThat(VpaVersionFetcher.getLatestVersions()).isNotEmpty()

            VpaVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val vpa =
                    when (component) {
                        is VPAComponent if (component == VPAComponent.Recommender) ->
                            VPA(version.toVpaRecommenderVersion())

                        is VPAComponent if (component == VPAComponent.Updater) ->
                            VPA(version.toVpaUpdaterVersion())

                        is VPAComponent if (component == VPAComponent.AdmissionController) ->
                            VPA(version.toVpaAdmissionControllerVersion())

                        else -> throw Exception("Unknown component: $component")
                    }

                vpa.render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
