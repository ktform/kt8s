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
import dev.ktform.kt8s.container.components.RubyComponent
import dev.ktform.kt8s.container.fetchers.RubyVersionFetcher
import dev.ktform.kt8s.container.packages.languages.ruby.CRuby
import dev.ktform.kt8s.container.packages.languages.ruby.MRuby
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class RubyTest {

    @Test
    fun testRuby() {
        runTest(timeout = 10.seconds) {
            assertThat(RubyVersionFetcher.getLatestVersions()).isNotEmpty()

            RubyVersionFetcher.getLatestVersions().forEach { (component, version) ->
                val cli =
                    when (component) {
                        is RubyComponent if (component == RubyComponent.Ruby) -> CRuby(version)

                        is RubyComponent if (component == RubyComponent.MRuby) -> MRuby(version)

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
