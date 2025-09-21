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
import dev.ktform.kt8s.container.components.PostgreSQLComponent
import dev.ktform.kt8s.container.fetchers.PostgreSQLVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class PostgreSQLVersion(
    val postgresqlVersions: Map<PostgreSQLComponent, String> = emptyMap()
) :
    Versions<PostgreSQLVersion>(
        postgresqlVersions.mapKeys { it.key as Component<PostgreSQLVersion> }
    ) {
    companion object : VersionsFetcher<PostgreSQLVersion> by PostgreSQLVersionFetcher {
        fun String.toPostgreSQLVersion(): PostgreSQLVersion =
            PostgreSQLVersion(mapOf(PostgreSQLComponent.PostgreSQL to this))

        fun flavours() = listOf(PostgreSQLComponent.CNPG, PostgreSQLComponent.Stackgres)
    }
}
