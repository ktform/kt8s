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
import dev.ktform.kt8s.charts.compute.DeschedulerChart
import dev.ktform.kt8s.container.versions.DeschedulerVersion

enum class DeschedulerComponent(val versions: DeschedulerVersion) : Component<DeschedulerVersion> {
    Descheduler(versions = DeschedulerVersion());

    override val charts: Set<Chart<DeschedulerVersion>> =
        setOf(DeschedulerChart(versions = versions))
    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
