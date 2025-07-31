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
package dev.ktform.kt8s.container.versions

import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.ScalaComponent
import dev.ktform.kt8s.container.components.SccacheComponent
import dev.ktform.kt8s.container.fetchers.ScalaVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class SccacheVersion(val sccacheVersions: Map<ScalaComponent, String> = emptyMap()) :
  Versions<SccacheVersion>(sccacheVersions.mapKeys { it.key as Component<SccacheVersion> }) {

  fun plus(other: SccacheVersion): SccacheVersion = SccacheVersion(sccacheVersions + other.sccacheVersions)

  companion object : VersionsFetcher<SccacheVersion> by SccacheVersionFetcher {
    fun String.toSccacheVersion(): SccacheVersion =
      SccacheVersion(mapOf(SccacheComponent.Sccache to this))
  }
}
