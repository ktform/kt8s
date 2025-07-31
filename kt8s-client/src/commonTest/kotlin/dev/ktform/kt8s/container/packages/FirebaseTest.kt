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
import dev.ktform.kt8s.container.components.FirebaseComponent
import dev.ktform.kt8s.container.fetchers.FirebaseVersionFetcher
import dev.ktform.kt8s.container.versions.FirebaseVersion.Companion.toFirebaseVersion
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class FirebaseTest {

    @Test
    fun testFirebase() {
        runTest(timeout = 10.seconds) {
            FirebaseVersionFetcher.getVersions().forEach { (component, versions) ->
                val cli =
                    when (component) {
                        is FirebaseComponent if (component == FirebaseComponent.Firebase) ->
                            Firebase(versions.last().toFirebaseVersion())

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
