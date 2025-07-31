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
import dev.ktform.kt8s.container.versions.BazelVersion

enum class BazelComponent(
    val versions: BazelVersion,
) : Component<BazelVersion> {
    Bazel(versions = BazelVersion()) {
      override val applicableFlavours: List<Component<*>> = Component.javaFlavours
    },

    Bazelisk(versions = BazelVersion()) {
      override val applicableFlavours: List<Component<*>> = Component.golangFlavours
    };

    override val charts: List<Chart<BazelVersion>> = emptyList()
}