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
 * @param data Data contains the secret data. Each key must consist of alphanumeric characters, '-',
 *   '_' or '.'. The serialized form of the secret data is a base64 encoded string, representing the
 *   arbitrary (possibly non-string) data value here. Described in
 *   https://tools.ietf.org/html/rfc4648#section-4
 * @param immutable Immutable, if set to true, ensures that data stored in the Secret cannot be
 *   updated (only object metadata can be modified). If not set to true, the field can be modified
 *   at any time. Defaulted to nil.
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param stringData stringData allows specifying non-binary secret data in string form. It is
 *   provided as a write-only input field for convenience. All keys and values are merged into the
 *   data field on write, overwriting any existing values. The stringData field is never output when
 *   reading from the API.
 * @param type Used to facilitate programmatic handling of secret data. More info:
 *   https://kubernetes.io/docs/concepts/configuration/secret/#secret-types
 */
@Serializable
public data class Secret(
    public val `data`: RawJsonObject,
    public val immutable: Boolean,
    public val metadata: ObjectMeta,
    public val stringData: String,
    public val type: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "v1"

    @Transient override val group: String = ""

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Secret"
}
