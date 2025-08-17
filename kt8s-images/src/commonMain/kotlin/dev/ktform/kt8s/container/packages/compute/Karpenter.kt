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
package dev.ktform.kt8s.container.packages.compute

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.KarpenterVersionFetcher
import dev.ktform.kt8s.container.versions.KarpenterVersion

class Karpenter(val versions: KarpenterVersion) : Renderable {

    override fun render(env: Environment): Either<String, String> =
        `package`.render(versions, KarpenterVersionFetcher, env)

    companion object {
        const val REPO = "https://github.com/kubernetes-sigs/karpenter"

        val DEFAULT_VERSIONS = listOf("1.6.1", "1.6.0", "1.5.3")

        val latest = DEFAULT_VERSIONS.first()

        val `package` =
            Package(
                packageName = "karpenter"
                //      repo = REPO,
                //      repoVersion = Package.withVPrefix,
            )
    }
}
