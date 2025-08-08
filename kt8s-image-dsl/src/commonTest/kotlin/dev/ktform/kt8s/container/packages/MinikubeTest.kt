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

class MinikubeTest {

  @Test
  fun testMinikube() {
    runTest {
      Environment.all.forEach { env ->
        val latest = Minikube.`package`.latestVersion()
        PackageTestCase("minikube", env, rendered = Minikube(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testMinikubeLatestVersions() {
    runTest {
      val latestNVersions = Minikube.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(Minikube.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(Minikube.DEFAULT_VERSIONS)
    }
  }
}