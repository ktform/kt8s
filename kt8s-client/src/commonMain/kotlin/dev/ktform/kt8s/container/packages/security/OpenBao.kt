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
package dev.ktform.kt8s.container.packages.security

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.OpenBaoVersionFetcher
import dev.ktform.kt8s.container.versions.OpenBaoVersion
import dev.ktform.kt8s.container.versions.OpenBaoVersion.Companion.toOpenBaoVersion

class OpenBao(val versions: OpenBaoVersion) : Renderable {
    constructor(version: String) : this(version.toOpenBaoVersion())

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, OpenBaoVersionFetcher, env)

    companion object {
        val DEFAULT_VERSIONS = listOf("2.3.2", "2.3.1")

        val latest = DEFAULT_VERSIONS.first()

        val `package` =
            Package(
                packageName = "openbao"
                //      repo = "https://github.com/openbao/openbao",
                //      repoVersion = Package.withVPrefix,
            )
    }
}
