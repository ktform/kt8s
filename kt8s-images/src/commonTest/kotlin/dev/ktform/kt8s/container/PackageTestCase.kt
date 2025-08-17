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

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.GoldenFileTestCases.Companion.getOrUpdateExpected
import kotlinx.io.files.Path

data class PackageTestCase(val name: String, val env: Environment, val rendered: String) {
    private val goldenFile =
        Path("src/commonTest/resources/packages/${name.lowercase().replace(" ", "_")}.json")

    fun isExpected() {
        assertThat(rendered)
            .isEqualTo(
                rendered.getOrUpdateExpected(
                    env.provider.name.lowercase() + "_" + env.distro.name.lowercase(),
                    goldenFile,
                )
            )
    }
}
