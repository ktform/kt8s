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

import dev.ktform.kt8s.container.Provider
import dev.ktform.kt8s.container.versions.RubyVersion

enum class RubyComponent(
    override val applicableFlavours: List<Component<*>> = emptyList(),
    override val applicableProviders: List<Provider> = Provider.all,
) : Component<RubyVersion> {
    Ruby,
    MRuby,
}
