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
import dev.ktform.kt8s.container.fetchers.RustVersionFetcher

class Rust(val versions: Versions.RustVersion) : Renderable  {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, RustVersionFetcher, env)


  companion object {
    val DEFAULT_VERSIONS = listOf(
      "1.89.0",
      "1.88.0",
      "1.87.0",
    )

    val `package` = Package(
      packageName = "rust",
//      repo = "https://github.com/rust-lang/rust",
//      repoVersion = Package.asIs,
    )
  }
}