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
import dev.ktform.kt8s.container.github.GithubClient
import io.github.z4kn4fein.semver.toVersion
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.GrypeVersionFetcher

class Grype(val versions: Versions.GrypeVersion) : Renderable  {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, GrypeVersionFetcher, env)


  companion object {
    val DEFAULT_VERSIONS = listOf(
      "0.97.2",
      "0.97.1",
      "0.97.0",
    )

    val `package` = Package(
      packageName = "grype",
      // repo = "https://github.com/anchore/grype",
      // repoVersion = Package.withVPrefix,
    )
  }
}
