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

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param data Data is the serialized representation of the state.
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param revision Revision indicates the revision of the state represented by Data.
 */
@Serializable
public data class ControllerRevision(
    public val `data`: RawJsonObject,
    public val metadata: ObjectMeta,
    public val revision: Long,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "apps/v1"

    @Transient override val group: String = "apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ControllerRevision"
}
