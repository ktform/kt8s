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
 * @param fieldRef Required: Selects a field of the pod: only annotations, labels, name, namespace
 *   and uid are supported.
 * @param mode Optional: mode bits used to set permissions on this file, must be an octal value
 *   between 0000 and 0777 or a decimal value between 0 and 511. YAML accepts both octal and decimal
 *   values, JSON requires decimal values for mode bits. If not specified, the volume defaultMode
 *   will be used. This might be in conflict with other options that affect the file mode, like
 *   fsGroup, and the result can be other mode bits set.
 * @param path Required: Path is the relative path name of the file to be created. Must not be
 *   absolute or contain the '..' path. Must be utf-8 encoded. The first item of the relative path
 *   must not start with '..'
 * @param resourceFieldRef Selects a resource of the container: only resources limits and requests
 *   (limits.cpu, limits.memory, requests.cpu and requests.memory) are currently supported.
 */
@Serializable
public data class DownwardAPIVolumeFile(
    public val fieldRef: ObjectFieldSelector,
    public val mode: Int,
    public val path: String,
    public val resourceFieldRef: ResourceFieldSelector,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "DownwardAPIVolumeFile"
}
