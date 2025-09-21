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
 * @param attachError attachError represents the last error encountered during attach operation, if
 *   any. This field must only be set by the entity completing the attach operation, i.e. the
 *   external-attacher.
 * @param attached attached indicates the volume is successfully attached. This field must only be
 *   set by the entity completing the attach operation, i.e. the external-attacher.
 * @param attachmentMetadata attachmentMetadata is populated with any information returned by the
 *   attach operation, upon successful attach, that must be passed into subsequent WaitForAttach or
 *   Mount calls. This field must only be set by the entity completing the attach operation, i.e.
 *   the external-attacher.
 * @param detachError detachError represents the last error encountered during detach operation, if
 *   any. This field must only be set by the entity completing the detach operation, i.e. the
 *   external-attacher.
 */
@Serializable
public data class VolumeAttachmentStatus(
    public val attachError: VolumeError,
    public val attached: Boolean,
    public val attachmentMetadata: RawJsonObject,
    public val detachError: VolumeError,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.storage/v1"

    @Transient override val group: String = "io.k8s.api.storage"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VolumeAttachmentStatus"
}
