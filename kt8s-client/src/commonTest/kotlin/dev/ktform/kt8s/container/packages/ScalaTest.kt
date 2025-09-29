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
package dev.ktform.kt8s.container.packages

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.ScalaComponent
import dev.ktform.kt8s.container.fetchers.ScalaVersionFetcher
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class ScalaTest {

    @Test
    fun testScala() {
        runTest(timeout = 10.seconds) {
            assertThat(ScalaVersionFetcher.getLatestVersions()).isNotEmpty()

            ScalaVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val cli =
                    when (component) {
                        is ScalaComponent if (component == ScalaComponent.Scala3) -> Scala(version)

                        is ScalaComponent if (component == ScalaComponent.Scala) -> Scala(version)

                        is ScalaComponent if (component == ScalaComponent.Sbt) -> Sbt(version)

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
