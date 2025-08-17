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
 * @param status status is the status of the ControllerModifyVolume operation. It can be in any of
 *   following states:
 *     - Pending Pending indicates that the PersistentVolumeClaim cannot be modified due to unmet
 *       requirements, such as the specified VolumeAttributesClass not existing.
 *     - InProgress InProgress indicates that the volume is being modified.
 *     - Infeasible Infeasible indicates that the request has been rejected as invalid by the CSI
 *       driver. To resolve the error, a valid VolumeAttributesClass needs to be specified. Note:
 *       New statuses can be added in the future. Consumers should check for unknown statuses and
 *       fail appropriately.
 *
 * @param targetVolumeAttributesClassName targetVolumeAttributesClassName is the name of the
 *   VolumeAttributesClass the PVC currently being reconciled
 */
@Serializable
public data class ModifyVolumeStatus(
    public val status: String,
    public val targetVolumeAttributesClassName: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ModifyVolumeStatus"
}
