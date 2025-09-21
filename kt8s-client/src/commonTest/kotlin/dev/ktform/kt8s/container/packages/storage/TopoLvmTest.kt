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
package dev.ktform.kt8s.container.packages.storage

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.TopoLvmComponent
import dev.ktform.kt8s.container.fetchers.TopoLvmVersionFetcher
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class TopoLvmTest {

    @Test
    fun testTopoLvm() {
        runTest(timeout = 10.seconds) {
            assertThat(TopoLvmVersionFetcher.getLatestVersions()).isNotEmpty()

            TopoLvmVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val topoLvm =
                    when (component) {
                        is TopoLvmComponent if (component == TopoLvmComponent.TopoLvm) ->
                            TopoLvm(version)

                        else -> throw Exception("Unknown component: $component")
                    }

                topoLvm
                    .render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name}: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}") },
                    )
            }
        }
    }
}
