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
package dev.ktform.kt8s.dev.ktform.kt8s.container.packages.languages.jre

import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class TemurinJreTest {

    @Test
    fun testOpenJ9Jre() {
        runTest(timeout = 1.seconds) {
            // Environment.all.forEach { env ->
            //   val latest = OpenJ9Jre.`package`.latestVersion().getOrElse { err -> throw
            // Exception("Unable to determine latest version: $err") }
            //   PackageTestCase(
            //     "openj9 jre",
            //     env,
            //     rendered = OpenJ9Jre(latest).render().getOrElse { err -> throw Exception("Unable
            // to render: $err") },
            //   ).isExpected()
            // }
        }
    }
}
