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
package dev.ktform.kt8s.container.components

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.charts.finops.OpenCostChart
import dev.ktform.kt8s.container.versions.OpenCostVersion

enum class OpenCostComponent(val versions: OpenCostVersion) : Component<OpenCostVersion> {
    OpenCost(versions = OpenCostVersion());

    override val charts: Set<Chart<OpenCostVersion>> = setOf(OpenCostChart(versions = versions))
    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
