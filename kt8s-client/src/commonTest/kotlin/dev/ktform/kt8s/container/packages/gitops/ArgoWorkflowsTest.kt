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
package dev.ktform.kt8s.dev.ktform.kt8s.container.packages.gitops

import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest

class ArgoWorkflowsTest {

    @Test
    fun testArgoWorkflows() {
        runTest(timeout = 10.seconds) {
            // val latest = ArgoWorkflows.`package`.latestVersion().getOrElse { err -> throw
            // Exception("Unable to determine latest version: $err") }

            // Environment.all.forEach { env ->
            //   PackageTestCase(
            //     "argo workflows",
            //     env,
            //     rendered = ArgoWorkflows(latest).render().getOrElse { err -> throw
            // Exception("Unable to render: $err") },
            //   ).isExpected()
            // }
        }
    }
}
