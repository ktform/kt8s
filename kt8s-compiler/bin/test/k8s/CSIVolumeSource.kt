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
 * @param driver driver is the name of the CSI driver that handles this volume. Consult with your
 *   admin for the correct name as registered in the cluster.
 * @param fsType fsType to mount. Ex. "ext4", "xfs", "ntfs". If not provided, the empty value is
 *   passed to the associated CSI driver which will determine the default filesystem to apply.
 * @param nodePublishSecretRef nodePublishSecretRef is a reference to the secret object containing
 *   sensitive information to pass to the CSI driver to complete the CSI NodePublishVolume and
 *   NodeUnpublishVolume calls. This field is optional, and may be empty if no secret is required.
 *   If the secret object contains more than one secret, all secret references are passed.
 * @param readOnly readOnly specifies a read-only configuration for the volume. Defaults to false
 *   (read/write).
 * @param volumeAttributes volumeAttributes stores driver-specific properties that are passed to the
 *   CSI driver. Consult your driver's documentation for supported values.
 */
@Serializable
public data class CSIVolumeSource(
    public val driver: String,
    public val fsType: String,
    public val nodePublishSecretRef: LocalObjectReference,
    public val readOnly: Boolean,
    public val volumeAttributes: RawJsonObject,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CSIVolumeSource"
}
