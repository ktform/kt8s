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

package dev.ktform.kt8s.container.fetchers

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import dev.ktform.kt8s.container.Component
import dev.ktform.kt8s.container.Versions
import dev.ktform.kt8s.container.VersionsFetcher

object ArgoVersionFetcher : VersionsFetcher<Versions.ArgoVersion> {
  override suspend fun getVersions(last: Int): Map<Component<Versions.ArgoVersion>, List<String>> {
    return emptyMap()
  }

  override fun repo(component: Component<Versions.ArgoVersion>): Option<String> = when (component) {
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoCD -> Some("argoproj")
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoWorkflows -> Some("argoproj")
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoEvents -> Some("argoproj")
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoRollouts -> Some("argoproj")
    else -> None
  }

  override fun String.toRepoVersion(component: Component<Versions.ArgoVersion>): Option<String> = when (component) {
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoCD -> Some("argoproj")
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoWorkflows -> Some("argoproj")
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoEvents -> Some("argoproj")
    is Versions.ArgoComponent if component == Versions.ArgoComponent.ArgoRollouts -> Some("argoproj")
    else -> None
  }

  override fun Component<Versions.ArgoVersion>.knownLatestVersions(): List<String> = when (this) {
    is Versions.ArgoComponent if this == Versions.ArgoComponent.ArgoCD -> emptyList()
    is Versions.ArgoComponent if this == Versions.ArgoComponent.ArgoWorkflows ->emptyList()
    is Versions.ArgoComponent if this == Versions.ArgoComponent.ArgoEvents -> emptyList()
    is Versions.ArgoComponent if this == Versions.ArgoComponent.ArgoRollouts -> emptyList()
    else -> emptyList()
  }
}