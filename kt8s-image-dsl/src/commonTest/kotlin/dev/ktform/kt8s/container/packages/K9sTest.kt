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

import arrow.core.getOrElse
import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class K9sTest {

  @Test
  fun testK9s() {
    runTest(timeout = 10.seconds) {
      val latest = K9s.`package`.latestVersion().getOrElse { err -> throw Exception("Unable to determine latest version: $err") }

      Environment.all.forEach { env ->
        PackageTestCase(
          "k9s",
          env,
          rendered = K9s(latest).render().getOrElse { err -> throw Exception("Unable to render: $err") },
        ).isExpected()
      }
    }
  }

  @Test
  fun testK9sLatestVersions() {
    runTest(timeout = 10.seconds) {
      val latestNVersions = K9s.`package`.availableVersions(Environment.default)
        .getOrElse { err -> throw Exception("Unable to determine available versions: $err") }
        .take(K9s.DEFAULT_VERSIONS.size)

      assertThat(latestNVersions).isEqualTo(K9s.DEFAULT_VERSIONS)
    }
  }
}