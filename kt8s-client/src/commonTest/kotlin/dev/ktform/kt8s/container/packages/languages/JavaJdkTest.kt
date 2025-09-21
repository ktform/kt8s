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
package dev.ktform.kt8s.container.packages.languages

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.GoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.container.components.JavaComponent
import dev.ktform.kt8s.container.fetchers.JavaVersionFetcher
import dev.ktform.kt8s.container.packages.languages.jdk.GraalVMJdk
import dev.ktform.kt8s.container.packages.languages.jdk.OpenJ9Jdk
import dev.ktform.kt8s.container.packages.languages.jdk.OpenJdk
import dev.ktform.kt8s.container.packages.languages.jre.GraalVMJre
import dev.ktform.kt8s.container.packages.languages.jre.OpenJ9Jre
import dev.ktform.kt8s.container.packages.languages.jre.OpenJdkJre
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class JavaJdkTest {

    @Test
    fun testJavaJdk() {
        runTest(timeout = 10.seconds) {
            assertThat(JavaVersionFetcher.getLatestVersions()).isNotEmpty()

            JavaVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val (jdk, jre) =
                    when (component) {
                        is JavaComponent if (component == JavaComponent.OpenJDK) ->
                            Pair(OpenJdk(version), OpenJdkJre(version))

                        is JavaComponent if (component == JavaComponent.OpenJ9) ->
                            Pair(OpenJ9Jdk(version), OpenJ9Jre(version))

                        is JavaComponent if (component == JavaComponent.GraalVM) ->
                            Pair(GraalVMJdk(version), GraalVMJre(version))

                        else -> throw Exception("Unknown component: $component")
                    }

                jdk.render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name} JDK: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}.jdk") },
                    )

                jre.render(env = Environment.default)
                    .fold(
                        { err -> throw Exception("Unable to render ${component.name} JRE: $err") },
                        { result -> result.getOrUpdateExpected("Dockerfile.${component.name}.jre") },
                    )
            }
        }
    }
}
