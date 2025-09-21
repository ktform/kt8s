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
 * @param binaryData BinaryData contains the binary data. Each key must consist of alphanumeric
 *   characters, '-', '_' or '.'. BinaryData can contain byte sequences that are not in the UTF-8
 *   range. The keys stored in BinaryData must not overlap with the ones in the Data field, this is
 *   enforced during validation process. Using this field will require 1.10+ apiserver and kubelet.
 * @param data Data contains the configuration data. Each key must consist of alphanumeric
 *   characters, '-', '_' or '.'. Values with non-UTF-8 byte sequences must use the BinaryData
 *   field. The keys stored in Data must not overlap with the keys in the BinaryData field, this is
 *   enforced during validation process.
 * @param immutable Immutable, if set to true, ensures that data stored in the ConfigMap cannot be
 *   updated (only object metadata can be modified). If not set to true, the field can be modified
 *   at any time. Defaulted to nil.
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 */
@Serializable
public data class ConfigMap(
    public val binaryData: RawJsonObject,
    public val `data`: RawJsonObject,
    public val immutable: Boolean,
    public val metadata: ObjectMeta,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "v1"

    @Transient override val group: String = ""

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ConfigMap"
}
