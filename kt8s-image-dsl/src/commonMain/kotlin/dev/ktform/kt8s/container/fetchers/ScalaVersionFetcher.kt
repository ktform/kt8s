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

 import dev.ktform.kt8s.container.Versions

 object ScalaVersionFetcher: Versions.VersionsFetcher<Versions.ScalaVersion> {
   override suspend fun getVersions(last: Int): Map<Versions.Component<Versions.ScalaVersion>, List<String>> {
     return emptyMap()
   }

   override fun repo(component: Versions.Component<Versions.ScalaVersion>): String {
     TODO("Not yet implemented")
   }

   override fun String.toRepoVersion(component: Versions.Component<Versions.ScalaVersion>): String {
     TODO("Not yet implemented")
   }

   override fun Versions.Component<Versions.ScalaVersion>.knownLatestVersions(): List<String> {
     TODO("Not yet implemented")
   }
 }