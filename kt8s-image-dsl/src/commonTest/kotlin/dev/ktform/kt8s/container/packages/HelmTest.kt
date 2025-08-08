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
class HelmTest {

  @Test
  fun testHelm() {
    runTest {
      Environment.all.forEach { env ->
        val latest = Helm.`package`.latestVersion()
        PackageTestCase("helm", env, rendered = Helm(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testHelmLatestVersions() {
    runTest {
      val latestNVersions = Helm.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(Helm.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(Helm.DEFAULT_VERSIONS)
    }
  }
}