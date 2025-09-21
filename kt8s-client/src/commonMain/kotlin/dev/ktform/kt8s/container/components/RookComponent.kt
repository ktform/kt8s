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
import dev.ktform.kt8s.charts.storage.RookChart
import dev.ktform.kt8s.container.versions.RookVersion

enum class RookComponent(val versions: RookVersion) : Component<RookVersion> {
    Rook(versions = RookVersion());

    override val charts: Set<Chart<RookVersion>> = setOf(RookChart(versions = versions))
    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
