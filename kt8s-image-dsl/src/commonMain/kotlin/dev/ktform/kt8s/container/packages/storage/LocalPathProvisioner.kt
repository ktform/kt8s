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

package dev.ktform.kt8s.container.packages.storage

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class LocalPathProvisioner(
  val version: String,
) : Renderable {
  override suspend fun versions(env: Environment): Either<String, List<String>> =
    `package`.versions(env)

  override suspend fun render(version: String, env: Environment): Either<String, String> =
    `package`.render(version, env)

  override suspend fun versions(): Either<String, List<String>> = `package`.versions(Environment.default)
  override suspend fun render(): Either<String, String> = `package`.render(version, Environment.default)

  companion object {
    val DEFAULT_VERSIONS = listOf(
      "0.0.32",
      "0.0.31",
      "0.0.30",
    )

    val `package` = Package(
      packageName = "local-path-provisioner",
      repo = "https://github.com/rancher/local-path-provisioner",
      repoVersion = Package.withVPrefix,
    )
  }
}
