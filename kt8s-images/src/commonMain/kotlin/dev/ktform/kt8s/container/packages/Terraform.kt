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

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.TerraformVersionFetcher

class Terraform(val versions: Versions.TerraformVersion) : Renderable {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, TerraformVersionFetcher, env)

  companion object {
    val DEFAULT_VERSIONS = listOf(
      "1.12.2",
      "1.12.1",
    )

    val `package` = Package(
      packageName = "terraform",
//      repo = "https://github.com/hashicorp/terraform",
//      repoVersion = Package.withVPrefix,
    )
  }
}