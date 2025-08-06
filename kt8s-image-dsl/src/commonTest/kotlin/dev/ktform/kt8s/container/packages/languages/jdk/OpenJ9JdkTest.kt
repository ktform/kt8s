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

package dev.ktform.kt8s.container.packages.languages.jdk

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData


class OpenJ9JdkTest: FunSpec(
  {
    context("OpenJ9 JDK") {
      withData(
        nameFn = { "openj9 jdk for ${it.name} ${it.env.distro.name} ${it.env.provider.name} should render correctly" },
        Environment.all.map { env ->
          PackageTestCase("openj9 jdk", env, rendered = OpenJ9Jdk().render())
        },
      ) {
        it.isExpected()
      }
    }
  },
)