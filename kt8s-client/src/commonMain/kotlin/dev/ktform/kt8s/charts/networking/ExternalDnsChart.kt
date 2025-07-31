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
import dev.ktform.kt8s.container.components.ExternalDnsComponent
import dev.ktform.kt8s.container.versions.ExternalDnsVersion

data class ExternalDnsChart(override val versions: ExternalDnsVersion) : Chart<ExternalDnsVersion> {
    override val group: ChartGroup = ChartGroup.Networking

    override fun getComponents(): List<ExternalDnsComponent> = listOf(ExternalDnsComponent.ExternalDns)

    override fun dependsOnGroups(): List<ChartGroup> = emptyList()

    override fun dependsOnCharts(): List<Chart<*>> = emptyList()
}
