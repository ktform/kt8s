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
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec spec defines a specification of a persistent volume owned by the cluster. Provisioned
 *   by an administrator. More info:
 *   https://kubernetes.io/docs/concepts/storage/persistent-volumes#persistent-volumes
 * @param status status represents the current information/status for the persistent volume.
 *   Populated by the system. Read-only. More info:
 *   https://kubernetes.io/docs/concepts/storage/persistent-volumes#persistent-volumes
 */
@Serializable
public data class PersistentVolume(
    public val metadata: ObjectMeta,
    public val spec: PersistentVolumeSpec,
    public val status: PersistentVolumeStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "v1"

    @Transient override val group: String = ""

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PersistentVolume"
}
