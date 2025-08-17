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
package dev.ktform.kt8s.container.packages.compute.karpenter

import arrow.core.Either
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Package
import dev.ktform.kt8s.container.Renderable
import dev.ktform.kt8s.container.fetchers.KarpenterVersionFetcher
import dev.ktform.kt8s.container.versions.KarpenterVersion

class KarpenterGCP(val versions: KarpenterVersion) : Renderable {

    override fun render(env: Environment): Either<String, String> =
        KarpenterAWS.`package`.render(versions, KarpenterVersionFetcher, env)

    companion object {
        val DEFAULT_VERSIONS = listOf("")

        val `package` =
            Package(
                packageName = "karpenter-provider-gcp"
                //      repo = "https://github.com/GoogleCloudPlatform/karpenter-provider-gcp",
                //      repoVersion = Package.withVPrefix,
            )
    }
}
