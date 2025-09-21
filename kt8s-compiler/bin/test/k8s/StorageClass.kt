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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param allowVolumeExpansion allowVolumeExpansion shows whether the storage class allow volume
 *   expand.
 * @param allowedTopologies allowedTopologies restrict the node topologies where volumes can be
 *   dynamically provisioned. Each volume plugin defines its own supported topology specifications.
 *   An empty TopologySelectorTerm list means there is no topology restriction. This field is only
 *   honored by servers that enable the VolumeScheduling feature.
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param mountOptions mountOptions controls the mountOptions for dynamically provisioned
 *   PersistentVolumes of this storage class. e.g. ["ro", "soft"]. Not validated - mount of the PVs
 *   will simply fail if one is invalid.
 * @param parameters parameters holds the parameters for the provisioner that should create volumes
 *   of this storage class.
 * @param provisioner provisioner indicates the type of the provisioner.
 * @param reclaimPolicy reclaimPolicy controls the reclaimPolicy for dynamically provisioned
 *   PersistentVolumes of this storage class. Defaults to Delete.
 * @param volumeBindingMode volumeBindingMode indicates how PersistentVolumeClaims should be
 *   provisioned and bound. When unset, VolumeBindingImmediate is used. This field is only honored
 *   by servers that enable the VolumeScheduling feature.
 */
@Serializable
public data class StorageClass(
    public val allowVolumeExpansion: Boolean,
    public val allowedTopologies: List<TopologySelectorTerm>,
    public val metadata: ObjectMeta,
    public val mountOptions: List<String>,
    public val parameters: RawJsonObject,
    public val provisioner: String,
    public val reclaimPolicy: String,
    public val volumeBindingMode: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "storage.k8s.io/v1"

    @Transient override val group: String = "storage.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "StorageClass"
}
