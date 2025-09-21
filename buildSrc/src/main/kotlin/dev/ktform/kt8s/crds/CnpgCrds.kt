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
package dev.ktform.kt8s.crds

import java.util.*

object CnpgCrds : Crds {
  override val helmRepo: Optional<Pair<String, String>> = Optional.of(Pair("cnpg", "https://cloudnative-pg.github.io/charts"))
  override val chartName: Optional<String> = Optional.of("cloudnative-pg")
}
