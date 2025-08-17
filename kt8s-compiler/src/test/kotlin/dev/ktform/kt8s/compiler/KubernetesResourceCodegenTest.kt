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
package dev.ktform.kt8s.compiler

import arrow.core.getOrElse
import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.compiler.CodegenGoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.compiler.JSONSchema.toResources
import kotlin.test.Test
import kotlinx.io.files.Path

class KubernetesResourceCodegenTest {

    val targetDir = CodegenGoldenFileTestCases.TARGET_DIR

    @Test
    fun testResources() {
        val schema = JSONSchema.Version.V1_33_0
        val defs =
            JSONSchema.load(schema).getOrElse {
                throw IllegalStateException("Failed to load schema $schema")
            }

        defs.toResources("dev.ktform.kt8s.resources").forEach { res ->
            val actual = res.toString().trimIndent().trim()
            assertThat(actual)
                .isEqualTo(actual.getOrUpdateExpected(Path("$targetDir/${res.kind}.kt")))
        }
    }
}
