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
package dev.ktform.kt8s.charts.development

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.ChartGroup
import dev.ktform.kt8s.charts.networking.CiliumChart
import dev.ktform.kt8s.charts.observability.PrometheusChart
import dev.ktform.kt8s.charts.security.CertManagerChart
import dev.ktform.kt8s.container.components.TheiaComponent
import dev.ktform.kt8s.container.versions.TheiaVersion

data class TheiaChart(override val versions: TheiaVersion) : Chart<TheiaVersion> {
    override val group: ChartGroup = ChartGroup.Development

    override val components: List<TheiaComponent> = listOf(TheiaComponent.Theia)

    override val dependsOnCharts: List<Chart<*>> =
        listOf(CertManagerChart(), CiliumChart(), PrometheusChart())
}
