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

 object AwsCliVersionFetcher: Versions.VersionsFetcher<Versions.AwsCliVersion> {
   override suspend fun getVersions(last: Int): Map<Versions.Component<Versions.AwsCliVersion>, List<String>> {
     return emptyMap()
   }

   override fun repo(component: Versions.Component<Versions.AwsCliVersion>): String {
     TODO("Not yet implemented")
   }

   override fun String.toRepoVersion(component: Versions.Component<Versions.AwsCliVersion>): String {
     TODO("Not yet implemented")
   }

   override fun Versions.Component<Versions.AwsCliVersion>.knownLatestVersions(): List<String> {
     TODO("Not yet implemented")
   }
 }