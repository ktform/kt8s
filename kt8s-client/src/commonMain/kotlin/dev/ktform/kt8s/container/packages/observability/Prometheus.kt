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
package dev.ktform.kt8s.container.packages.observability

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.PrometheusVersionFetcher
import dev.ktform.kt8s.container.versions.PrometheusVersion
import dev.ktform.kt8s.container.versions.PrometheusVersion.Companion.toPrometheusVersion

class Prometheus(val versions: PrometheusVersion) : Renderable {
    constructor(version: String) : this(version.toPrometheusVersion())

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, PrometheusVersionFetcher, env)

    companion object {
        val DEFAULT_VERSIONS = listOf("3.5.0", "3.4.2", "3.4.1", "3.4.0")

        val latest = DEFAULT_VERSIONS.first()

        val `package` =
            Package(
                packageName = "prometheus"
                //      repo = "https://github.com/prometheus/prometheus",
                //
                //      repoVersion = Package.withVPrefix,
            )
    }
}
