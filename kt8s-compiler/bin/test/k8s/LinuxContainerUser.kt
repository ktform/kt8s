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

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param gid GID is the primary gid initially attached to the first process in the container
 * @param supplementalGroups SupplementalGroups are the supplemental groups initially attached to
 *   the first process in the container
 * @param uid UID is the primary uid initially attached to the first process in the container
 */
@Serializable
public data class LinuxContainerUser(
    public val gid: Long,
    public val supplementalGroups: List<Long>,
    public val uid: Long,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "LinuxContainerUser"
}
