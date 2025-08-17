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
 * @param driverName Name of the CSI driver This field is immutable.
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param parameters parameters hold volume attributes defined by the CSI driver. These values are
 *   opaque to the Kubernetes and are passed directly to the CSI driver. The underlying storage
 *   provider supports changing these attributes on an existing volume, however the parameters field
 *   itself is immutable. To invoke a volume update, a new VolumeAttributesClass should be created
 *   with new parameters, and the PersistentVolumeClaim should be updated to reference the new
 *   VolumeAttributesClass.
 *
 * This field is required and must contain at least one key/value pair. The keys cannot be empty,
 * and the maximum number of parameters is 512, with a cumulative max size of 256K. If the CSI
 * driver rejects invalid parameters, the target PersistentVolumeClaim will be set to an
 * "Infeasible" state in the modifyVolumeStatus field.
 */
@Serializable
public data class VolumeAttributesClass(
    public val driverName: String,
    public val metadata: ObjectMeta,
    public val parameters: RawJsonObject,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "storage.k8s.io/v1alpha1"

    @Transient override val group: String = "storage.k8s.io"

    @Transient override val version: String = "v1alpha1"

    @SerialName("kind") override val kind: String = "VolumeAttributesClass"
}
