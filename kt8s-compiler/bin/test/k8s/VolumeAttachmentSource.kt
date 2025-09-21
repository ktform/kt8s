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
 * @param inlineVolumeSpec inlineVolumeSpec contains all the information necessary to attach a
 *   persistent volume defined by a pod's inline VolumeSource. This field is populated only for the
 *   CSIMigration feature. It contains translated fields from a pod's inline VolumeSource to a
 *   PersistentVolumeSpec. This field is beta-level and is only honored by servers that enabled the
 *   CSIMigration feature.
 * @param persistentVolumeName persistentVolumeName represents the name of the persistent volume to
 *   attach.
 */
@Serializable
public data class VolumeAttachmentSource(
    public val inlineVolumeSpec: PersistentVolumeSpec,
    public val persistentVolumeName: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.storage/v1"

    @Transient override val group: String = "io.k8s.api.storage"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VolumeAttachmentSource"
}
