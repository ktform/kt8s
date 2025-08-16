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

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.PythonVersionFetcher

class Python(val versions: Versions.PythonVersion) : Renderable  {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, PythonVersionFetcher, env)


  companion object {
    val DEFAULT_VERSIONS = listOf(
      "3.13.6",
      "3.13.5",
      "3.13.4",
    )

    val `package` = Package(
      packageName = "python",
//      repo = "https://github.com/python/cpython",
//      repoVersion = Package.withVPrefix,
    )
  }
}