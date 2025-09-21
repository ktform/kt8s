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

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.right
import arrow.core.toNonEmptyListOrThrow
import dev.ktform.kt8s.container.fetchers.VersionsFetcher
import dev.ktform.kt8s.container.versions.Versions

/**
 * A package is a collection of standardized Dockerfile instructions that are used to build a
 * [Renderable] container image.
 */
data class Package(
    val packageName: String,
    val providers: NonEmptyList<Provider> = Provider.all.toNonEmptyListOrThrow(),
    val buildDependencies: Map<Distro, Set<String>> = emptyMap(),
    val runDependencies: Map<Distro, Set<String>> = emptyMap(),
    val buildPackageDependencies: Map<Distro, Set<Package>> = emptyMap(),
    val runPackageDependencies: Map<Distro, Set<Package>> = emptyMap(),
    val buildCommands: (Environment, List<Package>) -> List<String> = { _, _ -> emptyList() },
    val distrolessCommands: (Environment, List<Package>) -> List<String> = { _, _ -> emptyList() },
    val stopGracefullySignal: Signal = defaultStopGracefullySignal,
    val stopImmediatelySignal: Signal = defaultStopImmediatelySignal,
    val reloadConfigSignal: Signal = defaultReloadConfigSignal,
) {
    fun <T : Versions<T>> render(
        versions: Versions<T>,
        fetcher: VersionsFetcher<T>,
        env: Environment,
    ): Either<String, String> {
        return "${fetcher::class.simpleName}".right()
    }

    companion object {
        val defaultStopGracefullySignal = Signal.SIGTERM
        val defaultStopImmediatelySignal = Signal.SIGINT
        val defaultReloadConfigSignal = Signal.SIGHUP
    }
}
