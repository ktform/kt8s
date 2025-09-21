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
 * @param fsType fsType is the filesystem type to mount. It applies only when the Path is a block
 *   device. Must be a filesystem type supported by the host operating system. Ex. "ext4", "xfs",
 *   "ntfs". The default value is to auto-select a filesystem if unspecified.
 * @param path path of the full path to the volume on the node. It can be either a directory or
 *   block device (disk, partition, ...).
 */
@Serializable
public data class LocalVolumeSource(public val fsType: String, public val path: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "LocalVolumeSource"
}
