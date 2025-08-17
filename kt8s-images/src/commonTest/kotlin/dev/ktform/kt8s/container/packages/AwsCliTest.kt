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

import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class AwsCliTest {

    @Test
    fun testAwsCliTools() {
        runTest(timeout = 10.seconds) {
            //      val latest = AwsCli.`package`.latestVersion().getOrElse { err -> throw
            // Exception("Unable to determine latest version: $err") }
            //
            //      Environment.all.forEach { env ->
            //        PackageTestCase(
            //          "awscli",
            //          env,
            //          rendered = AwsCli(latest).render().getOrElse { err -> throw
            // Exception("Unable to render: $err") },
            //        ).isExpected()
            //      }
        }
    }

    @Test
    fun testAwsCliToolsLatestVersions() {
        runTest(timeout = 10.seconds) {
            //      val latestNVersions = AwsCli.`package`.availableVersions(Environment.default)
            //        .getOrElse { err -> throw Exception("Unable to determine available versions:
            // $err") }
            //        .take(AwsCli.DEFAULT_VERSIONS.size)
            //
            //      assertThat(latestNVersions).isEqualTo(AwsCli.DEFAULT_VERSIONS)
        }
    }
}
