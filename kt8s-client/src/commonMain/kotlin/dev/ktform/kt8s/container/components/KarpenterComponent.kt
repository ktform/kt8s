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
import dev.ktform.kt8s.container.Provider
import dev.ktform.kt8s.container.versions.KarpenterVersion

enum class KarpenterComponent(
    override val versions: KarpenterVersion,
) : Component<KarpenterVersion> {
    Karpenter(versions = KarpenterVersion()) {
      override val applicableProviders: List<Provider> = Provider.all
    }
    AWS(versions = KarpenterVersion(), applicableProviders = listOf(Provider.AWS)) {
      override val applicableProviders: List<Provider> = Provider.all
    },
    AWSNodeTerminatorController(versions = KarpenterVersion(), applicableProviders = listOf(Provider.AWS)) {
      override val applicableProviders: List<Provider> = Provider.all
    },
    GCP(versions = KarpenterVersion(), applicableProviders = listOf(Provider.GCP)) {
      override val applicableProviders: List<Provider> = Provider.all
    },
    Azure(versions = KarpenterVersion(), applicableProviders = listOf(Provider.Azure)) {
      override val applicableProviders: List<Provider> = Provider.all
    };

    override val charts: List<Chart<KarpenterVersion>> = emptyList()
    override val applicableFlavours: List<Component<*>> = emptyList()
}
