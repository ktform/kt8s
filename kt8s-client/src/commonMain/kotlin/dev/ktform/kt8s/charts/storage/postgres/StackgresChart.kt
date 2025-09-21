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
package dev.ktform.kt8s.charts.storage.postgres

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.ChartGroup
import dev.ktform.kt8s.charts.networking.CiliumChart
import dev.ktform.kt8s.charts.security.CertManagerChart
import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.PostgreSQLComponent
import dev.ktform.kt8s.container.versions.PostgreSQLVersion
import dev.ktform.kt8s.container.versions.Versions

data class StackgresChart(override val versions: Versions<PostgreSQLVersion>) :
    Chart<PostgreSQLVersion> {
    override val group: ChartGroup = ChartGroup.Storage

    override val components: List<Component<PostgreSQLVersion>> =
        listOf(PostgreSQLComponent.Stackgres)

    override val dependsOnCharts: List<Chart<*>> = listOf(CertManagerChart(), CiliumChart())
}
