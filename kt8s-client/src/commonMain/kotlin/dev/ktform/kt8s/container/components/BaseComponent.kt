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

import arrow.core.NonEmptySet
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.versions.BaseVersion

enum class BaseComponent(override val appliedVersions: BaseVersion) : Component<BaseVersion> {
    Base(appliedVersions = BaseVersion()) {
        override fun image(env: Environment): String {
            return ""
        }

        override val applicableFlavours: Set<Component<*>> = emptySet()
    },
    Development(appliedVersions = BaseVersion()) {
        override fun image(env: Environment): String {
            return ""
        }

        override val applicableFlavours: Set<Component<*>> =
            NonEmptySet(
                Base,
                Component.buildNative +
                    Component.buildJvm +
                    Component.buildPython +
                    Component.golangFlavours +
                    Component.javaFlavours +
                    Component.pythonFlavours +
                    Component.dotNetFlavours +
                    Component.denoFlavours +
                    Component.rubyFlavours +
                    Component.rustFlavours +
                    Component.nodeFlavours +
                    Component.cloudManagementCli +
                    Component.containerSecurityCli,
            )
    },
    Distroless(appliedVersions = BaseVersion()) {
        override fun image(env: Environment): String {
            return ""
        }

        override val applicableFlavours: Set<Component<*>> = setOf(Base)
    },
}
