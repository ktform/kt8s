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
import dev.ktform.kt8s.container.versions.Kt8sVersion

enum class Kt8sComponent(override val appliedVersions: Kt8sVersion) : Component<Kt8sVersion> {
    Kt8sDashboard(appliedVersions = Kt8sVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    },
    Kt8sCostReporter(appliedVersions = Kt8sVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    },
    Kt8sController(appliedVersions = Kt8sVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    },
    Kt8sScaler(appliedVersions = Kt8sVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    },
    Kt8sApp(appliedVersions = Kt8sVersion()) {
        override fun image(env: Environment): String {
            return ""
        }
    };

    override val applicableFlavours: Set<Component<*>> = Component.golangFlavours
}
