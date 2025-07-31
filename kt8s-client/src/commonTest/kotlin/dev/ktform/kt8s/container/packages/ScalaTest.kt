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
package dev.ktform.kt8s.dev.ktform.kt8s.container.packages

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.ScalaComponent
import dev.ktform.kt8s.container.fetchers.ScalaVersionFetcher
import dev.ktform.kt8s.container.versions.ScalaVersion.Companion.toScalaVersion
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class ScalaTest {

    @Test
    fun testScala() {
        runTest(timeout = 10.seconds) {
            ScalaVersionFetcher.getVersions().forEach { (component, versions) ->
                val cli =
                    when (component) {
                        is ScalaComponent if (component == ScalaComponent.Scala) ->
                            Scala(versions.last().toScalaVersion())

                        is ScalaComponent if (component == ScalaComponent.Sbt) ->
                            Sbt(versions.last().toScalaVersion())
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
