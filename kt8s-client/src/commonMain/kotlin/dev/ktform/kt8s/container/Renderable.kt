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

/** A [Renderable] represents a Dockerfile that can be rendered into a string. */
interface Renderable {
    /**
     * Renders the Dockerfile into a string.
     *
     * @param env The environment to render the Dockerfile for.
     * @return An [Either] containing the rendered Dockerfile or an error message.
     */
    fun render(env: Environment = Environment.default): Either<String, String>
}
