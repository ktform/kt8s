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
import dev.ktform.kt8s.container.components.JavaComponent
import dev.ktform.kt8s.container.fetchers.JavaVersionFetcher
import dev.ktform.kt8s.container.fetchers.VersionsFetcher

data class JavaVersion(val javaVersions: Map<JavaComponent, String> = emptyMap()) :
    Versions<JavaVersion>(javaVersions.mapKeys { it.key as Component<JavaVersion> }) {
    fun plus(other: JavaVersion): JavaVersion = JavaVersion(javaVersions + other.javaVersions)

    companion object : VersionsFetcher<JavaVersion> by JavaVersionFetcher {
        fun String.toOpenJ9Version(): JavaVersion = JavaVersion(mapOf(JavaComponent.OpenJ9 to this))

        fun String.toOpenJDKVersion(): JavaVersion =
            JavaVersion(mapOf(JavaComponent.OpenJDK to this))

        fun String.toGraalVmVersion(): JavaVersion =
            JavaVersion(mapOf(JavaComponent.GraalVM to this))
    }
}
