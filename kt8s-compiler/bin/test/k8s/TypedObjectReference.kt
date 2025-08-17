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
 * @param apiGroup APIGroup is the group for the resource being referenced. If APIGroup is not
 *   specified, the specified Kind must be in the core API group. For any other third-party types,
 *   APIGroup is required.
 * @param name Name is the name of resource being referenced
 * @param namespace Namespace is the namespace of resource being referenced Note that when a
 *   namespace is specified, a gateway.networking.k8s.io/ReferenceGrant object is required in the
 *   referent namespace to allow that namespace's owner to accept the reference. See the
 *   ReferenceGrant documentation for details. (Alpha) This field requires the
 *   CrossNamespaceVolumeDataSource feature gate to be enabled.
 */
@Serializable
public data class TypedObjectReference(
    public val apiGroup: String,
    public val name: String,
    public val namespace: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "TypedObjectReference"
}
