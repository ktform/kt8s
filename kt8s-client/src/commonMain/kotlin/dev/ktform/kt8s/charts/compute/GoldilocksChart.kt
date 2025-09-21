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
package dev.ktform.kt8s.charts.compute

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.ChartGroup
import dev.ktform.kt8s.charts.observability.PrometheusChart
import dev.ktform.kt8s.container.components.GoldilocksComponent
import dev.ktform.kt8s.container.versions.GoldilocksVersion

data class GoldilocksChart(override val versions: GoldilocksVersion) : Chart<GoldilocksVersion> {
    override val group: ChartGroup = ChartGroup.Compute

    override val components: List<GoldilocksComponent> = listOf(GoldilocksComponent.Goldilocks)

    override val dependsOnCharts: List<Chart<*>> = listOf(PrometheusChart())
}
