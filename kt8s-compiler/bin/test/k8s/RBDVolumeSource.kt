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
 * @param fsType fsType is the filesystem type of the volume that you want to mount. Tip: Ensure
 *   that the filesystem type is supported by the host operating system. Examples: "ext4", "xfs",
 *   "ntfs". Implicitly inferred to be "ext4" if unspecified. More info:
 *   https://kubernetes.io/docs/concepts/storage/volumes#rbd
 * @param image image is the rados image name. More info:
 *   https://examples.k8s.io/volumes/rbd/README.md#how-to-use-it
 * @param keyring keyring is the path to key ring for RBDUser. Default is /etc/ceph/keyring. More
 *   info: https://examples.k8s.io/volumes/rbd/README.md#how-to-use-it
 * @param monitors monitors is a collection of Ceph monitors. More info:
 *   https://examples.k8s.io/volumes/rbd/README.md#how-to-use-it
 * @param pool pool is the rados pool name. Default is rbd. More info:
 *   https://examples.k8s.io/volumes/rbd/README.md#how-to-use-it
 * @param readOnly readOnly here will force the ReadOnly setting in VolumeMounts. Defaults to false.
 *   More info: https://examples.k8s.io/volumes/rbd/README.md#how-to-use-it
 * @param secretRef secretRef is name of the authentication secret for RBDUser. If provided
 *   overrides keyring. Default is nil. More info:
 *   https://examples.k8s.io/volumes/rbd/README.md#how-to-use-it
 * @param user user is the rados user name. Default is admin. More info:
 *   https://examples.k8s.io/volumes/rbd/README.md#how-to-use-it
 */
@Serializable
public data class RBDVolumeSource(
    public val fsType: String,
    public val image: String,
    public val keyring: String,
    public val monitors: List<String>,
    public val pool: String,
    public val readOnly: Boolean,
    public val secretRef: LocalObjectReference,
    public val user: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "RBDVolumeSource"
}
