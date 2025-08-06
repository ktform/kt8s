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

interface Renderable {
  fun versions(env: Environment = Environment.default): List<String>
  fun render(version: String = latestVersion(), env: Environment = Environment.default): String
  fun latestVersion(env: Environment = Environment.default): String =
    versions(env).maxByOrNull { it }.toOption().getOrElse { throw Exception("Unable to determine latest version") }
}