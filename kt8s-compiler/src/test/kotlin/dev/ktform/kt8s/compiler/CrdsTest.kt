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

import com.varabyte.truthish.assertWithMessage
import kotlin.test.Test

class CrdsTest {

    @Test
    fun testChartFiles() {
        assertWithMessage("Charts list should not be empty").that(Crds.chartFiles()).isNotEmpty()
    }

    @Test
    fun testCrdsLoading() {
        val result = Crds.all()

        assertWithMessage("CRD loading should be successful").that(result.isRight()).isTrue()

        result.onRight {
            assertWithMessage("CRD map should not be empty").that(it).isNotEmpty()
            it.forEach { (chart, definitions) ->
                assertWithMessage("Definitions for chart '$chart' should not be empty")
                    .that(definitions)
                    .isNotEmpty()
            }
        }
    }
}
