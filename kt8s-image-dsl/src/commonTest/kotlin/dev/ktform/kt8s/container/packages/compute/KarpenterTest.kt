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

class KarpenterTest {

  @Test
  fun testKarpenter() {
    runTest {
      Environment.all.forEach { env ->
        val latest = Karpenter.`package`.latestVersion()
        PackageTestCase("karpenter", env, rendered = Karpenter(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testKarpenterLatestVersions() {
    runTest {
      val latestNVersions = Karpenter.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(Karpenter.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(Karpenter.DEFAULT_VERSIONS)
    }
  }
}