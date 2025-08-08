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

package dev.ktform.kt8s.container

import arrow.core.getOrElse
import arrow.core.toOption
import dev.ktform.kt8s.container.packages.ArgoCD.Companion.`package`

interface Renderable {
  suspend fun versions(env: Environment = Environment.default): List<String>
  suspend fun render(version: String, env: Environment = Environment.default): String

  suspend fun versions(): List<String>
  suspend fun render(): String

  suspend fun latestVersion(env: Environment = Environment.default): String =
    versions(env).maxByOrNull { it }.toOption().getOrElse { throw Exception("Unable to determine latest version") }
}