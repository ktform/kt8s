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

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GradleTest {


  @Test
  fun testGradle() {
    runTest {
      Environment.all.forEach { env ->
        val latest = Gradle.`package`.latestVersion()
        PackageTestCase("gradle", env, rendered = Gradle(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testGradleLatestVersions() {
    runTest {
      val latestNVersions = Gradle.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(Gradle.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(Gradle.DEFAULT_VERSIONS)
    }
  }
}