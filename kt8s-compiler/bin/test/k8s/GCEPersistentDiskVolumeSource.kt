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
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param fsType fsType is filesystem type of the volume that you want to mount. Tip: Ensure that
 *   the filesystem type is supported by the host operating system. Examples: "ext4", "xfs", "ntfs".
 *   Implicitly inferred to be "ext4" if unspecified. More info:
 *   https://kubernetes.io/docs/concepts/storage/volumes#gcepersistentdisk
 * @param partition partition is the partition in the volume that you want to mount. If omitted, the
 *   default is to mount by volume name. Examples: For volume /dev/sda1, you specify the partition
 *   as "1". Similarly, the volume partition for /dev/sda is "0" (or you can leave the property
 *   empty). More info: https://kubernetes.io/docs/concepts/storage/volumes#gcepersistentdisk
 * @param pdName pdName is unique name of the PD resource in GCE. Used to identify the disk in GCE.
 *   More info: https://kubernetes.io/docs/concepts/storage/volumes#gcepersistentdisk
 * @param readOnly readOnly here will force the ReadOnly setting in VolumeMounts. Defaults to false.
 *   More info: https://kubernetes.io/docs/concepts/storage/volumes#gcepersistentdisk
 */
@Serializable
public data class GCEPersistentDiskVolumeSource(
    public val fsType: String,
    public val partition: Int,
    public val pdName: String,
    public val readOnly: Boolean,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "GCEPersistentDiskVolumeSource"
}
