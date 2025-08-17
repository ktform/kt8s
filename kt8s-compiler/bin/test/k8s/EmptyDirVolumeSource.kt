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
 * @param medium medium represents what type of storage medium should back this directory. The
 *   default is "" which means to use the node's default medium. Must be an empty string (default)
 *   or Memory. More info: https://kubernetes.io/docs/concepts/storage/volumes#emptydir
 * @param sizeLimit sizeLimit is the total amount of local storage required for this EmptyDir
 *   volume. The size limit is also applicable for memory medium. The maximum usage on memory medium
 *   EmptyDir would be the minimum value between the SizeLimit specified here and the sum of memory
 *   limits of all containers in a pod. The default is nil which means that the limit is undefined.
 *   More info: https://kubernetes.io/docs/concepts/storage/volumes#emptydir
 */
@Serializable
public data class EmptyDirVolumeSource(
    public val medium: String,
    public val sizeLimit: StringOrNumber,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "EmptyDirVolumeSource"
}
