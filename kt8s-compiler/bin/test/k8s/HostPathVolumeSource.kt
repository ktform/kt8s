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
 * @param path path of the directory on the host. If the path is a symlink, it will follow the link
 *   to the real path. More info: https://kubernetes.io/docs/concepts/storage/volumes#hostpath
 * @param type type for HostPath Volume Defaults to "" More info:
 *   https://kubernetes.io/docs/concepts/storage/volumes#hostpath
 */
@Serializable
public data class HostPathVolumeSource(public val path: String, public val type: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "HostPathVolumeSource"
}
