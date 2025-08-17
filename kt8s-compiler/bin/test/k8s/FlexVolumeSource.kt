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
 * @param driver driver is the name of the driver to use for this volume.
 * @param fsType fsType is the filesystem type to mount. Must be a filesystem type supported by the
 *   host operating system. Ex. "ext4", "xfs", "ntfs". The default filesystem depends on FlexVolume
 *   script.
 * @param options options is Optional: this field holds extra command options if any.
 * @param readOnly readOnly is Optional: defaults to false (read/write). ReadOnly here will force
 *   the ReadOnly setting in VolumeMounts.
 * @param secretRef secretRef is Optional: secretRef is reference to the secret object containing
 *   sensitive information to pass to the plugin scripts. This may be empty if no secret object is
 *   specified. If the secret object contains more than one secret, all secrets are passed to the
 *   plugin scripts.
 */
@Serializable
public data class FlexVolumeSource(
    public val driver: String,
    public val fsType: String,
    public val options: RawJsonObject,
    public val readOnly: Boolean,
    public val secretRef: LocalObjectReference,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "FlexVolumeSource"
}
