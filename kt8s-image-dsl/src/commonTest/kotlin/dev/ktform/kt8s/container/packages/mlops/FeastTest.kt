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

package dev.ktform.kt8s.container.packages.mlops

import arrow.core.getOrElse
import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class FeastTest {

  @Test
  fun testFeast() {
    runTest(timeout = 10.seconds) {
//      val latest = Feast.`package`.latestVersion().getOrElse { err -> throw Exception("Unable to determine latest version: $err") }
//
//      Environment.all.forEach { env ->
//        PackageTestCase(
//          "feast",
//          env,
//          rendered = Feast(latest).render().getOrElse { err -> throw Exception("Unable to render: $err") },
//        ).isExpected()
//      }
    }
  }

  @Test
  fun testFeastLatestVersions() {
    runTest(timeout = 10.seconds) {
//      val latestNVersions = Feast.`package`.availableVersions(Environment.default)
//        .getOrElse { err -> throw Exception("Unable to determine available versions: $err") }
//        .take(Feast.DEFAULT_VERSIONS.size)
//
//      assertThat(latestNVersions).isEqualTo(Feast.DEFAULT_VERSIONS)
    }
  }
}