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
import dev.ktform.kt8s.charts.observability.MimirChart
import dev.ktform.kt8s.container.versions.MimirVersion

enum class MimirComponent(val versions: MimirVersion) : Component<MimirVersion> {
    Mimir(versions = MimirVersion());

    override val charts: Set<Chart<MimirVersion>> = setOf(MimirChart(versions = versions))
    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
