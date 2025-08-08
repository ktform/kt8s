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

package dev.ktform.kt8s.container.packages.languages

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
class GolangTest {


  @Test
  fun testGolang() {
    runTest {
      Environment.all.forEach { env ->
        val latest = Golang.`package`.latestVersion()
        PackageTestCase("golang", env, rendered = Golang(latest).render()).isExpected()
      }
    }
  }

  @Test
  fun testGolangLatestVersions() {
    runTest {
      val latestNVersions = Golang.`package`.availableVersions(Environment.default)
        .sortedByDescending { it }
        .take(Golang.DEFAULT_VERSIONS.size)
      assertThat(latestNVersions).isEqualTo(Golang.DEFAULT_VERSIONS)
    }
  }
}