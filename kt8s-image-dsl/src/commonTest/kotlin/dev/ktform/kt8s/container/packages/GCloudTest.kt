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

class GCloudTest {

  @Test
  fun testGCloud() {
    runTest {
      Environment.all.forEach { env ->
        val latest = GCloud.`package`.latestVersion()
        PackageTestCase("gcloud", env, rendered = GCloud(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testGCloudLatestVersions() {
    runTest {
      val latestNVersions = GCloud.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(GCloud.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(GCloud.DEFAULT_VERSIONS)
    }
  }
}