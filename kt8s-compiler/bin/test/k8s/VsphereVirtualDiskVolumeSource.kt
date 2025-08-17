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
 * @param fsType fsType is filesystem type to mount. Must be a filesystem type supported by the host
 *   operating system. Ex. "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if unspecified.
 * @param storagePolicyID storagePolicyID is the storage Policy Based Management (SPBM) profile ID
 *   associated with the StoragePolicyName.
 * @param storagePolicyName storagePolicyName is the storage Policy Based Management (SPBM) profile
 *   name.
 * @param volumePath volumePath is the path that identifies vSphere volume vmdk
 */
@Serializable
public data class VsphereVirtualDiskVolumeSource(
    public val fsType: String,
    public val storagePolicyID: String,
    public val storagePolicyName: String,
    public val volumePath: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VsphereVirtualDiskVolumeSource"
}
