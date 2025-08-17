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
package dev.ktform.kt8s.container.packages.security

import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class KyvernoTest {

    @Test
    fun testKyverno() {
        runTest(timeout = 10.seconds) {
            //      val latest = Kyverno.`package`.latestVersion().getOrElse { err -> throw
            // Exception("Unable to determine latest version: $err") }
            //
            //      Environment.all.forEach { env ->
            //        PackageTestCase(
            //          "kyverno",
            //          env,
            //          rendered = Kyverno(latest).render().getOrElse { err -> throw
            // Exception("Unable to render: $err") },
            //        ).isExpected()
            //      }
        }
    }

    @Test
    fun testKyvernoLatestVersions() {
        runTest(timeout = 10.seconds) {
            //      val latestNVersions = Kyverno.`package`.availableVersions(Environment.default)
            //        .getOrElse { err -> throw Exception("Unable to determine available versions:
            // $err") }
            //        .take(Kyverno.DEFAULT_VERSIONS.size)
            //
            //      assertThat(latestNVersions).isEqualTo(Kyverno.DEFAULT_VERSIONS)
        }
    }
}
