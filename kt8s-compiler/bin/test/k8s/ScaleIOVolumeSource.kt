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
 * @param fsType fsType is the filesystem type to mount. Must be a filesystem type supported by the
 *   host operating system. Ex. "ext4", "xfs", "ntfs". Default is "xfs".
 * @param gateway gateway is the host address of the ScaleIO API Gateway.
 * @param protectionDomain protectionDomain is the name of the ScaleIO Protection Domain for the
 *   configured storage.
 * @param readOnly readOnly Defaults to false (read/write). ReadOnly here will force the ReadOnly
 *   setting in VolumeMounts.
 * @param secretRef secretRef references to the secret for ScaleIO user and other sensitive
 *   information. If this is not provided, Login operation will fail.
 * @param sslEnabled sslEnabled Flag enable/disable SSL communication with Gateway, default false
 * @param storageMode storageMode indicates whether the storage for a volume should be
 *   ThickProvisioned or ThinProvisioned. Default is ThinProvisioned.
 * @param storagePool storagePool is the ScaleIO Storage Pool associated with the protection domain.
 * @param system system is the name of the storage system as configured in ScaleIO.
 * @param volumeName volumeName is the name of a volume already created in the ScaleIO system that
 *   is associated with this volume source.
 */
@Serializable
public data class ScaleIOVolumeSource(
    public val fsType: String,
    public val gateway: String,
    public val protectionDomain: String,
    public val readOnly: Boolean,
    public val secretRef: LocalObjectReference,
    public val sslEnabled: Boolean,
    public val storageMode: String,
    public val storagePool: String,
    public val system: String,
    public val volumeName: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ScaleIOVolumeSource"
}
