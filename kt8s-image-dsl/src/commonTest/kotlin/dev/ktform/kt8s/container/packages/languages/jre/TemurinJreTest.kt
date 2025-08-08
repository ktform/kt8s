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

package dev.ktform.kt8s.container.packages.languages.jre

import arrow.core.getOrElse
import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class TemurinJreTest {

  @Test
  fun testOpenJ9Jre() {
    runTest(timeout = 1.seconds) {
      Environment.all.forEach { env ->
        val latest = OpenJ9Jre.`package`.latestVersion().getOrElse { err -> throw Exception("Unable to determine latest version: $err") }
        PackageTestCase("openj9 jre", env, rendered = OpenJ9Jre(latest).render().getOrElse { err ->throw Exception("Unable to render: $err") }).isExpected()
      }
    }
  }

  @Test
  fun testOpenJ9JreLatestVersions() {
    runTest(timeout = 1.seconds) {
      val latestNVersions = OpenJ9Jre.`package`.availableVersions(Environment.default).getOrElse { err -> throw Exception("Unable to determine available versions: $err") }
        .sortedByDescending { it }
        .take(OpenJ9Jre.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(OpenJ9Jre.DEFAULT_VERSIONS)
    }
  }
}