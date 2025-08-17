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
package dev.ktform.kt8s.container

import dev.ktform.kt8s.container.components.Component

data class Environment(
    val name: String = "default",
    val provider: Provider,
    val distro: Distro,
    val flavours: List<Component<*>>,
) {
    companion object {
        val all: List<Environment> =
            Distro.all.flatMap { (_, distro) ->
                Provider.entries.map { Environment(it.name, it, distro, listOf()) }
            }

        internal val default =
            Environment(
                name = "default",
                provider = Provider.Local,
                distro = Distro.Debian,
                listOf(),
            )
    }
}
