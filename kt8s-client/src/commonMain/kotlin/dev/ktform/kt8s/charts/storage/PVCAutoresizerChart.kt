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
package dev.ktform.kt8s.charts.storage

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.ChartGroup
import dev.ktform.kt8s.charts.networking.CiliumChart
import dev.ktform.kt8s.charts.security.CertManagerChart
import dev.ktform.kt8s.container.components.PVCAutoresizerComponent
import dev.ktform.kt8s.container.versions.PVCAutoresizerVersion

data class PVCAutoresizerChart(override val versions: PVCAutoresizerVersion) :
    Chart<PVCAutoresizerVersion> {
    override val group: ChartGroup = ChartGroup.Storage

    override val components: List<PVCAutoresizerComponent> =
        listOf(PVCAutoresizerComponent.PVCAutoresizer)

    override val dependsOnCharts: List<Chart<*>> = listOf(CertManagerChart(), CiliumChart())
}
