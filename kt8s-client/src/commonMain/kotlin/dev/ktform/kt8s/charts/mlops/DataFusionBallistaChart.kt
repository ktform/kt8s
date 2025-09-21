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
package dev.ktform.kt8s.charts.mlops

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.ChartGroup
import dev.ktform.kt8s.charts.networking.CiliumChart
import dev.ktform.kt8s.charts.security.CertManagerChart
import dev.ktform.kt8s.container.components.DataFusionBallistaComponent
import dev.ktform.kt8s.container.versions.DataFusionBallistaVersion

data class DataFusionBallistaChart(override val versions: DataFusionBallistaVersion) :
    Chart<DataFusionBallistaVersion> {
    override val group: ChartGroup = ChartGroup.MlOps

    override val components: List<DataFusionBallistaComponent> =
        listOf(DataFusionBallistaComponent.DataFusionBallista)

    override val dependsOnCharts: List<Chart<*>> = listOf(CertManagerChart(), CiliumChart())
}
