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
package dev.ktform.kt8s.container.github

actual fun getGithubToken(): String? {
    return try {
        // On Android, we can try to access system properties or environment
        System.getenv("GITHUB_TOKEN") ?: System.getProperty("github.token")
    } catch (e: Exception) {
        null
    }
}
