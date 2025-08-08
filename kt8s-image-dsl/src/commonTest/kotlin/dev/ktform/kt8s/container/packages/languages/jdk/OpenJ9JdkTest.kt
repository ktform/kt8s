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

package dev.ktform.kt8s.container.packages.languages.jdk

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class OpenJ9JdkTest {

  @Test
  fun testOpenJ9Jdk() {
    runTest {
      Environment.all.forEach { env ->
        val latest = OpenJ9Jdk.`package`.latestVersion()
        PackageTestCase("openj9 jdk", env, rendered = OpenJ9Jdk(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testOpenJ9JdkLatestVersions() {
    runTest {
      val latestNVersions = OpenJ9Jdk.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(OpenJ9Jdk.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(OpenJ9Jdk.DEFAULT_VERSIONS)
    }
  }
}