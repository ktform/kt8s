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
 * @param errorCode errorCode is a numeric gRPC code representing the error encountered during
 *   Attach or Detach operations.
 *
 * This is an optional, alpha field that requires the MutableCSINodeAllocatableCount feature gate
 * being enabled to be set.
 *
 * @param message message represents the error encountered during Attach or Detach operation. This
 *   string may be logged, so it should not contain sensitive information.
 * @param time time represents the time the error was encountered.
 */
@Serializable
public data class VolumeError(
    public val errorCode: Int,
    public val message: String,
    public val time: KubernetesTime,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.storage/v1"

    @Transient override val group: String = "io.k8s.api.storage"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VolumeError"
}
