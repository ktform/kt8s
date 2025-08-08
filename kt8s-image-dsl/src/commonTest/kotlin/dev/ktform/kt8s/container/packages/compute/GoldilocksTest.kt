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

package dev.ktform.kt8s.container.packages.compute

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GoldilocksTest {

  @Test
  fun testGoldilocks() {
    runTest {
      Environment.all.forEach { env ->
        val latest = Goldilocks.`package`.latestVersion()
        PackageTestCase("goldilocks", env, rendered = Goldilocks(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testGoldilocksLatestVersions() {
    runTest {
      val latestNVersions = Goldilocks.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(Goldilocks.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(Goldilocks.DEFAULT_VERSIONS)
    }
  }
}