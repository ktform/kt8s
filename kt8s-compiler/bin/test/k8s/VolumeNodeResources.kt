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

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param count count indicates the maximum number of unique volumes managed by the CSI driver that
 *   can be used on a node. A volume that is both attached and mounted on a node is considered to be
 *   used once, not twice. The same rule applies for a unique volume that is shared among multiple
 *   pods on the same node. If this field is not specified, then the supported number of volumes on
 *   this node is unbounded.
 */
@Serializable
public data class VolumeNodeResources(public val count: Int) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.storage/v1"

    @Transient override val group: String = "io.k8s.api.storage"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VolumeNodeResources"
}
