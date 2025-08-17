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
 * @param whenDeleted WhenDeleted specifies what happens to PVCs created from StatefulSet
 *   VolumeClaimTemplates when the StatefulSet is deleted. The default policy of `Retain` causes
 *   PVCs to not be affected by StatefulSet deletion. The `Delete` policy causes those PVCs to be
 *   deleted.
 * @param whenScaled WhenScaled specifies what happens to PVCs created from StatefulSet
 *   VolumeClaimTemplates when the StatefulSet is scaled down. The default policy of `Retain` causes
 *   PVCs to not be affected by a scaledown. The `Delete` policy causes the associated PVCs for any
 *   excess pods above the replica count to be deleted.
 */
@Serializable
public data class StatefulSetPersistentVolumeClaimRetentionPolicy(
    public val whenDeleted: String,
    public val whenScaled: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind")
    override val kind: String = "StatefulSetPersistentVolumeClaimRetentionPolicy"
}
