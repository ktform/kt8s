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

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.versions.BazelVersion

enum class BazelComponent(override val appliedVersions: BazelVersion) : Component<BazelVersion> {
    Bazel(appliedVersions = BazelVersion()) {
        override val applicableFlavours: Set<Component<*>> = Component.javaFlavours

        override fun image(env: Environment): String {
            return ""
        }
    },
    Bazelisk(appliedVersions = BazelVersion()) {
        override val applicableFlavours: Set<Component<*>> = Component.golangFlavours

        override fun image(env: Environment): String {
            return ""
        }
    },
}
