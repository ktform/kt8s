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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param fsType fsType is the filesystem type to mount. Must be a filesystem type supported by the
 *   host operating system. Ex. "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if
 *   unspecified.
 * @param lun lun is Optional: FC target lun number
 * @param readOnly readOnly is Optional: Defaults to false (read/write). ReadOnly here will force
 *   the ReadOnly setting in VolumeMounts.
 * @param targetWWNs targetWWNs is Optional: FC target worldwide names (WWNs)
 * @param wwids wwids Optional: FC volume world wide identifiers (wwids) Either wwids or combination
 *   of targetWWNs and lun must be set, but not both simultaneously.
 */
@Serializable
public data class FCVolumeSource(
    public val fsType: String,
    public val lun: Int,
    public val readOnly: Boolean,
    public val targetWWNs: List<String>,
    public val wwids: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "FCVolumeSource"
}
