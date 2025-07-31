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
import dev.ktform.kt8s.charts.gitops.ArgoCDChart
import dev.ktform.kt8s.charts.gitops.ArgoEventsChart
import dev.ktform.kt8s.charts.gitops.ArgoRolloutsChart
import dev.ktform.kt8s.charts.gitops.ArgoWorkflowsChart
import dev.ktform.kt8s.container.Provider
import dev.ktform.kt8s.container.versions.ArgoVersion
import dev.ktform.kt8s.container.versions.Versions

enum class ArgoComponent(versions: ArgoVersion) : Component<ArgoVersion> {
    ArgoCD(versions = ArgoVersion()),
    ArgoRollouts(versions = ArgoVersion()),
    ArgoWorkflows(versions = ArgoVersion()),
    ArgoEvents(versions = ArgoVersion());

  override val applicableFlavours: List<Component<*>> = Component.golangFlavours

  override val charts: List<Chart<ArgoVersion>> = listOf(
    ArgoCDChart(versions = versions),
    ArgoRolloutsChart(versions = versions),
    ArgoWorkflowsChart(versions = versions),
    ArgoEventsChart(versions = versions),
  )
}
