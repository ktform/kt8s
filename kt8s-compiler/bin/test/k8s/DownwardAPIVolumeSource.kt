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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param defaultMode Optional: mode bits to use on created files by default. Must be a Optional:
 *   mode bits used to set permissions on created files by default. Must be an octal value between
 *   0000 and 0777 or a decimal value between 0 and 511. YAML accepts both octal and decimal values,
 *   JSON requires decimal values for mode bits. Defaults to 0644. Directories within the path are
 *   not affected by this setting. This might be in conflict with other options that affect the file
 *   mode, like fsGroup, and the result can be other mode bits set.
 * @param items Items is a list of downward API volume file
 */
@Serializable
public data class DownwardAPIVolumeSource(
    public val defaultMode: Int,
    public val items: List<DownwardAPIVolumeFile>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "DownwardAPIVolumeSource"
}
