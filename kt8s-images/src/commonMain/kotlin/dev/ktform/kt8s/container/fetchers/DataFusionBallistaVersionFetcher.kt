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

import arrow.core.Option
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.versions.DataFusionBallistaVersion

object DataFusionBallistaVersionFetcher : VersionsFetcher<DataFusionBallistaVersion> {
    override suspend fun getVersions(
        last: Int
    ): Map<Component<DataFusionBallistaVersion>, List<String>> {
        return emptyMap()
    }

    override fun repo(component: Component<DataFusionBallistaVersion>): Option<String> {
        TODO("Not yet implemented")
    }

    override fun String.toRepoVersion(
        component: Component<DataFusionBallistaVersion>
    ): Option<String> {
        TODO("Not yet implemented")
    }

    override fun Component<DataFusionBallistaVersion>.knownLatestVersions(): List<String> {
        TODO("Not yet implemented")
    }
}
