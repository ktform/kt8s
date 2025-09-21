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
import dev.ktform.kt8s.container.components.DeschedulerComponent
import dev.ktform.kt8s.container.versions.DeschedulerVersion

data class DeschedulerChart(override val versions: DeschedulerVersion) : Chart<DeschedulerVersion> {
    override val group: ChartGroup = ChartGroup.Compute

    override val components: List<DeschedulerComponent> = listOf(DeschedulerComponent.Descheduler)
}
