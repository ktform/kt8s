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
package dev.ktform.kt8s.container.packages.languages.jdk

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.JavaVersionFetcher
import dev.ktform.kt8s.container.versions.JavaVersion
import dev.ktform.kt8s.container.versions.JavaVersion.Companion.toOpenJDKVersion

class OpenJdk(val versions: JavaVersion) : Renderable {
    constructor(version: String) : this(version.toOpenJDKVersion())

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, JavaVersionFetcher, env)

    companion object {

        val `package` = Package(packageName = "OpenJdk")
    }
}
