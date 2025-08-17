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

package dev.ktform.kt8s.container.packages.security

import arrow.core.getOrElse
import dev.ktform.kt8s.container.Environment
import kotlinx.coroutines.test.runTest
import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.PackageTestCase
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class CertManagerTest {

  @Test
  fun testCertManager() {
    runTest(timeout = 10.seconds) {
//      val latest = CertManager.`package`.latestVersion().getOrElse { err -> throw Exception("Unable to determine latest version: $err") }
//
//      Environment.all.forEach { env ->
//        PackageTestCase(
//          "cert-manager",
//          env,
//          rendered = CertManager(latest).render().getOrElse { err -> throw Exception("Unable to render: $err") },
//        ).isExpected()
//      }
    }
  }

  @Test
  fun testCertManagerLatestVersions() {
    runTest(timeout = 10.seconds) {
//      val latestNVersions = CertManager.`package`.availableVersions(Environment.default)
//        .getOrElse { err -> throw Exception("Unable to determine available versions: $err") }
//        .take(CertManager.DEFAULT_VERSIONS.size)
//
//      assertThat(latestNVersions).isEqualTo(CertManager.DEFAULT_VERSIONS)
    }
  }
}