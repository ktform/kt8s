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

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import dev.ktform.kt8s.container.packages.Argo
import kotlin.test.Test

class JavaJdkTest{

  @Test
  fun testJavaJdk() {
    Environment.all.forEach { env ->
      PackageTestCase("java jdk", env, rendered = JavaJdk().render()).isExpected()
    }
  }
}