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

package dev.ktform.kt8s.container.fetchers

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import dev.ktform.kt8s.container.Component
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.VersionsFetcher


object AlloyVersionFetcher : VersionsFetcher<Versions.AlloyVersion> {
  override suspend fun getVersions(last: Int): Map<Component<Versions.AlloyVersion>, List<String>> {
    return emptyMap()
  }

  override fun repo(component: Component<Versions.AlloyVersion>): Option<String> = when (component) {
    is Versions.AlloyComponent if component == Versions.AlloyComponent.Alloy -> Some("alloy")
    else -> None
  }

  override fun String.toRepoVersion(component: Component<Versions.AlloyVersion>): Option<String> = when (component) {
    is Versions.AlloyComponent if component == Versions.AlloyComponent.Alloy -> Some("https://github.com/grafana/alloy")
    else -> None
  }

  override fun Component<Versions.AlloyVersion>.knownLatestVersions(): List<String> = listOf(
    "1.10.1",
    "1.10.0",
    "1.9.2",
  )
}