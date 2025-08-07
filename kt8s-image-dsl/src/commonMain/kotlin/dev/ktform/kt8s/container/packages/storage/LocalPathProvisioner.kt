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

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable

class LocalPathProvisioner  (val version: String = dev.ktform.kt8s.container.packages.storage.LocalPathProvisioner.Companion.`package`.latestVersion(Environment.default)) : Renderable {
  override fun versions(env: Environment): List<String> = dev.ktform.kt8s.container.packages.storage.LocalPathProvisioner.Companion.`package`.versions(env)
  override fun render(version: String, env: Environment): String = dev.ktform.kt8s.container.packages.storage.LocalPathProvisioner.Companion.`package`.render(version, env)

  companion object {
    val `package` = Package(
      packageName = "localpathprovisioner",
      repo = ""
    )
  }
}
