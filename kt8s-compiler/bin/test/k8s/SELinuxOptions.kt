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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param level Level is SELinux level label that applies to the container.
 * @param role Role is a SELinux role label that applies to the container.
 * @param type Type is a SELinux type label that applies to the container.
 * @param user User is a SELinux user label that applies to the container.
 */
@Serializable
public data class SELinuxOptions(
    public val level: String,
    public val role: String,
    public val type: String,
    public val user: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SELinuxOptions"
}
