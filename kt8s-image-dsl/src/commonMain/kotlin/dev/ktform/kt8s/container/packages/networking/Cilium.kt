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

package dev.ktform.kt8s.container.packages.networking

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.CiliumVersionFetcher

class Cilium(val versions: Versions.CiliumVersion) : Renderable  {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, CiliumVersionFetcher, env)


  companion object {
    val DEFAULT_VERSIONS = listOf(
      "1.18.0",
      "1.17.6",
    )

    val latest = DEFAULT_VERSIONS.first()

    val `package` = Package(
      packageName = "cilium",
      // repo = "https://github.com/cilium/cilium",
      // repoVersion = Package.withVPrefix,
    )
  }
}