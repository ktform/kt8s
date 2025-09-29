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
import dev.ktform.kt8s.container.versions.VeleroVersion

enum class VeleroComponent(override val appliedVersions: VeleroVersion) : Component<VeleroVersion> {
    Velero(appliedVersions = VeleroVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    };

    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
