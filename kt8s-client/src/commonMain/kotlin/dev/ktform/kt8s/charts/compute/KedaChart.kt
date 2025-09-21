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
import dev.ktform.kt8s.container.components.KedaComponent
import dev.ktform.kt8s.container.versions.KedaVersion

data class KedaChart(override val versions: KedaVersion) : Chart<KedaVersion> {
    override val group: ChartGroup = ChartGroup.Compute

    override val components: List<KedaComponent> = listOf(KedaComponent.Keda)

    override val dependsOnCharts: List<Chart<*>> = listOf(PrometheusChart())
}
