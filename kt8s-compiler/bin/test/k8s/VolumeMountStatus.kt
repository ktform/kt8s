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
 * @param mountPath MountPath corresponds to the original VolumeMount.
 * @param name Name corresponds to the name of the original VolumeMount.
 * @param readOnly ReadOnly corresponds to the original VolumeMount.
 * @param recursiveReadOnly RecursiveReadOnly must be set to Disabled, Enabled, or unspecified (for
 *   non-readonly mounts). An IfPossible value in the original VolumeMount must be translated to
 *   Disabled or Enabled, depending on the mount result.
 */
@Serializable
public data class VolumeMountStatus(
    public val mountPath: String,
    public val name: String,
    public val readOnly: Boolean,
    public val recursiveReadOnly: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VolumeMountStatus"
}
