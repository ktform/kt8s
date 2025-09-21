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
import dev.ktform.kt8s.charts.observability.AlloyChart
import dev.ktform.kt8s.container.versions.AlloyVersion

enum class AlloyComponent(versions: AlloyVersion) : Component<AlloyVersion> {
    Alloy(versions = AlloyVersion());

    override val charts: Set<Chart<AlloyVersion>> = setOf(AlloyChart(versions = versions))
    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
