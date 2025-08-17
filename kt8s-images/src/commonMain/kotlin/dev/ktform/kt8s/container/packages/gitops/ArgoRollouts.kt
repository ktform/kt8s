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

package dev.ktform.kt8s.container.packages.gitops

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.fetchers.ArgoVersionFetcher

data class ArgoRollouts(val versions: Versions.ArgoVersion) : Renderable {

  override fun render(
    env: Environment,
  ): Either<String, String> = `package`.render(versions, ArgoVersionFetcher, env)

  companion object {
    const val REPO = "https://github.com/argoproj/argo-rollouts"

    val DEFAULT_VERSIONS = listOf(
      "1.8.3",
      "1.8.2",
      "1.8.1",
    )

    val latest = DEFAULT_VERSIONS.first()

    val `package` = Package(
      packageName = "argo-rollouts",
//      repo = REPO,
//      repoVersion = Package.withVPrefix,
    )
  }
}
