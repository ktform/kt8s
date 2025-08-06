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

package dev.ktform.kt8s.container.kt8s.compute

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.PackageTestCase
import dev.ktform.kt8s.container.kt8s.compute.karpenter.KarpenterGCP
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData

class ClusterAutoscalerTest: FunSpec(
  {
    context("Cluster Autoscaler") {
      withData(
        nameFn = { "Cluster Autoscaler for ${it.name} ${it.env.distro.name} ${it.env.provider.name} should render correctly" },
        Environment.all.map { env ->
          PackageTestCase("cluster autoscaler", env, rendered = ClusterAutoscaler().render())
        },
      ) {
        it.isExpected()
      }
    }
  },
)