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
package dev.ktform.kt8s.container.components

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.charts.storage.postgres.CNPGChart
import dev.ktform.kt8s.charts.storage.postgres.StackgresChart
import dev.ktform.kt8s.container.versions.PostgreSQLVersion

enum class PostgreSQLComponent(val versions: PostgreSQLVersion) : Component<PostgreSQLVersion> {
    PostgreSQL(versions = PostgreSQLVersion()),
    Stackgres(versions = PostgreSQLVersion()),
    CNPG(versions = PostgreSQLVersion());

    override val charts: Set<Chart<PostgreSQLVersion>> =
        setOf(StackgresChart(versions = versions), CNPGChart(versions = versions))

    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
