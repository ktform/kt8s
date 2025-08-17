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

object NodeJsVersionFetcher : VersionsFetcher<Versions.NodeJsVersion> {
  override suspend fun getVersions(last: Int): Map<Component<Versions.NodeJsVersion>, List<String>> {
    return emptyMap()
  }

  override fun repo(component: Component<Versions.NodeJsVersion>): Option<String> {
    TODO("Not yet implemented")
  }

  override fun String.toRepoVersion(component: Component<Versions.NodeJsVersion>): Option<String> {
    TODO("Not yet implemented")
  }

  override fun Component<Versions.NodeJsVersion>.knownLatestVersions(): List<String> {
    TODO("Not yet implemented")
  }
}