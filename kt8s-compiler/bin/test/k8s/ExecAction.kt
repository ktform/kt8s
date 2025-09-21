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
package dev.ktform.kt8s.resources

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param command Command is the command line to execute inside the container, the working directory
 *   for the command is root ('/') in the container's filesystem. The command is simply exec'd, it
 *   is not run inside a shell, so traditional shell instructions ('|', etc) won't work. To use a
 *   shell, you need to explicitly call out to that shell. Exit status of 0 is treated as
 *   live/healthy and non-zero is unhealthy.
 */
@Serializable
public data class ExecAction(public val command: List<String>) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ExecAction"
}
