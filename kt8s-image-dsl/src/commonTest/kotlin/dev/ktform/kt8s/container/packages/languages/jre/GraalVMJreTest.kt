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

package dev.ktform.kt8s.container.packages.languages.jre

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import dev.ktform.kt8s.container.packages.Argo
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData


class GraalVMJreTest: FunSpec(
  {
    context("GraalVM JRE") {
      withData(
        nameFn = { "graalvm jre for ${it.name} ${it.env.distro.name} ${it.env.provider.name} should render correctly" },
        Environment.all.map { env ->
          PackageTestCase("graalvm jre", env, rendered = GraalVMJre().render())
        },
      ) {
        it.isExpected()
      }
    }
  },
)