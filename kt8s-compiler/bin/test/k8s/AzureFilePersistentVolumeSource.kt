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
 * @param readOnly readOnly defaults to false (read/write). ReadOnly here will force the ReadOnly
 *   setting in VolumeMounts.
 * @param secretName secretName is the name of secret that contains Azure Storage Account Name and
 *   Key
 * @param secretNamespace secretNamespace is the namespace of the secret that contains Azure Storage
 *   Account Name and Key default is the same as the Pod
 * @param shareName shareName is the azure Share Name
 */
@Serializable
public data class AzureFilePersistentVolumeSource(
    public val readOnly: Boolean,
    public val secretName: String,
    public val secretNamespace: String,
    public val shareName: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "AzureFilePersistentVolumeSource"
}
