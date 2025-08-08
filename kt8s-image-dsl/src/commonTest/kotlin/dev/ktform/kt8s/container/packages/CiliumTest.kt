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
import dev.ktform.kt8s.container.packages.networking.Cilium
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CiliumTest {

  @Test
  fun testCilium() {
    runTest {
      Environment.all.forEach { env ->
        val latest = Cilium.`package`.latestVersion()
        PackageTestCase("cilium", env, rendered = Cilium(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testCiliumLatestVersions() {
    runTest {
      val latestNVersions = Cilium.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(Cilium.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(Cilium.DEFAULT_VERSIONS)
    }
  }
}