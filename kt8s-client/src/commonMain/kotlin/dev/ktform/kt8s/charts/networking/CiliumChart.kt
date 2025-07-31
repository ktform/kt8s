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
package dev.ktform.kt8s.charts.networking

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.ChartGroup
import dev.ktform.kt8s.container.components.CiliumComponent
import dev.ktform.kt8s.container.versions.CiliumVersion

data class CiliumChart(override val versions: CiliumVersion) : Chart<CiliumVersion> {
    override val group: ChartGroup = ChartGroup.Networking

    override fun getComponents(): List<CiliumComponent> = listOf(CiliumComponent.Cilium)

    override fun dependsOnGroups(): List<ChartGroup> = emptyList()

    override fun dependsOnCharts(): List<Chart<*>> = emptyList()
}
