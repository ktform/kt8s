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

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param endpoints endpoints is the endpoint name that details Glusterfs topology. More info:
 *   https://examples.k8s.io/volumes/glusterfs/README.md#create-a-pod
 * @param endpointsNamespace endpointsNamespace is the namespace that contains Glusterfs endpoint.
 *   If this field is empty, the EndpointNamespace defaults to the same namespace as the bound PVC.
 *   More info: https://examples.k8s.io/volumes/glusterfs/README.md#create-a-pod
 * @param path path is the Glusterfs volume path. More info:
 *   https://examples.k8s.io/volumes/glusterfs/README.md#create-a-pod
 * @param readOnly readOnly here will force the Glusterfs volume to be mounted with read-only
 *   permissions. Defaults to false. More info:
 *   https://examples.k8s.io/volumes/glusterfs/README.md#create-a-pod
 */
@Serializable
public data class GlusterfsPersistentVolumeSource(
    public val endpoints: String,
    public val endpointsNamespace: String,
    public val path: String,
    public val readOnly: Boolean,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "GlusterfsPersistentVolumeSource"
}
