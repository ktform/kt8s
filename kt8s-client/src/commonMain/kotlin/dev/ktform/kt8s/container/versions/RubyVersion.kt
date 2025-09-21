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
package dev.ktform.kt8s.container.versions

import dev.ktform.kt8s.container.components.Component
import dev.ktform.kt8s.container.components.RubyComponent
import dev.ktform.kt8s.container.fetchers.RubyVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class RubyVersion(val rubyVersions: Map<RubyComponent, String> = emptyMap()) :
    Versions<RubyVersion>(rubyVersions.mapKeys { it.key as Component<RubyVersion> }) {
    companion object : VersionsFetcher<RubyVersion> by RubyVersionFetcher {
        fun String.toRubyVersion(): RubyVersion = RubyVersion(mapOf(RubyComponent.Ruby to this))

        fun String.toMRubyVersion(): RubyVersion = RubyVersion(mapOf(RubyComponent.MRuby to this))
    }
}
