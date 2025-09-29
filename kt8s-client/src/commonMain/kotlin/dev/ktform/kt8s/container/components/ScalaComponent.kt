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
import dev.ktform.kt8s.container.versions.ScalaVersion

enum class ScalaComponent(override val appliedVersions: ScalaVersion) : Component<ScalaVersion> {
    Scala3(appliedVersions = ScalaVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    },
    Scala(appliedVersions = ScalaVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    },
    Sbt(appliedVersions = ScalaVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    };

    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
