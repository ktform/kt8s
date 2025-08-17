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
 * @param apiGroup APIGroup is the group for the resource being referenced
 * @param name Name is the name of resource being referenced
 */
@Serializable
public data class RoleRef(public val apiGroup: String, public val name: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.rbac/v1"

    @Transient override val group: String = "io.k8s.api.rbac"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "RoleRef"
}
