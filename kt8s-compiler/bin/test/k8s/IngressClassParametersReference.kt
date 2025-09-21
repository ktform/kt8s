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
 * @param apiGroup apiGroup is the group for the resource being referenced. If APIGroup is not
 *   specified, the specified Kind must be in the core API group. For any other third-party types,
 *   APIGroup is required.
 * @param name name is the name of resource being referenced.
 * @param namespace namespace is the namespace of the resource being referenced. This field is
 *   required when scope is set to "Namespace" and must be unset when scope is set to "Cluster".
 * @param scope scope represents if this refers to a cluster or namespace scoped resource. This may
 *   be set to "Cluster" (default) or "Namespace".
 */
@Serializable
public data class IngressClassParametersReference(
    public val apiGroup: String,
    public val name: String,
    public val namespace: String,
    public val scope: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "IngressClassParametersReference"
}
