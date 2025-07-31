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
import dev.ktform.kt8s.container.components.TheiaComponent
import dev.ktform.kt8s.container.versions.TheiaVersion

data class TheiaChart(override val versions: TheiaVersion) : Chart<TheiaVersion> {
    override val group: ChartGroup = ChartGroup.Compute

    override fun getComponents(): List<TheiaComponent> {
        TODO("Not yet implemented")
    }

    override fun dependsOnGroups(): List<ChartGroup> {
        TODO("Not yet implemented")
    }

    override fun dependsOnCharts(): List<Chart<*>> {
        TODO("Not yet implemented")
    }
}
