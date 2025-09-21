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
 * @param fsType fsType Filesystem type to mount. Must be a filesystem type supported by the host
 *   operating system. Examples: "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if
 *   unspecified. More info: https://examples.k8s.io/mysql-cinder-pd/README.md
 * @param readOnly readOnly is Optional: Defaults to false (read/write). ReadOnly here will force
 *   the ReadOnly setting in VolumeMounts. More info:
 *   https://examples.k8s.io/mysql-cinder-pd/README.md
 * @param secretRef secretRef is Optional: points to a secret object containing parameters used to
 *   connect to OpenStack.
 * @param volumeID volumeID used to identify the volume in cinder. More info:
 *   https://examples.k8s.io/mysql-cinder-pd/README.md
 */
@Serializable
public data class CinderPersistentVolumeSource(
    public val fsType: String,
    public val readOnly: Boolean,
    public val secretRef: SecretReference,
    public val volumeID: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CinderPersistentVolumeSource"
}
