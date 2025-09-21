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
import dev.ktform.kt8s.charts.gitops.ArgoCdChart
import dev.ktform.kt8s.charts.gitops.ArgoEventsChart
import dev.ktform.kt8s.charts.gitops.ArgoRolloutsChart
import dev.ktform.kt8s.charts.gitops.ArgoWorkflowsChart
import dev.ktform.kt8s.container.versions.ArgoVersion

enum class ArgoComponent(versions: ArgoVersion) : Component<ArgoVersion> {
    ArgoCd(versions = ArgoVersion()),
    ArgoRollouts(versions = ArgoVersion()),
    ArgoWorkflows(versions = ArgoVersion()),
    ArgoEvents(versions = ArgoVersion());

    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours

    override val charts: Set<Chart<ArgoVersion>> =
        setOf(
            ArgoCdChart(versions = versions),
            ArgoRolloutsChart(versions = versions),
            ArgoWorkflowsChart(versions = versions),
            ArgoEventsChart(versions = versions),
        )
}
