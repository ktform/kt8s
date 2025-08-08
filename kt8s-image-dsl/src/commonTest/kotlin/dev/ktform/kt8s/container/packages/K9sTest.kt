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

class K9sTest {

  @Test
  fun testK9s() {
    runTest {
      Environment.all.forEach { env ->
        val latest = K9s.`package`.latestVersion()
        PackageTestCase("k9s", env, rendered = K9s(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testK9sLatestVersions() {
    runTest {
      val latestNVersions = K9s.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(K9s.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(K9s.DEFAULT_VERSIONS)
    }
  }
}