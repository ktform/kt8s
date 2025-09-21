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
 * @param readOnly readOnly here will force the Quobyte volume to be mounted with read-only
 *   permissions. Defaults to false.
 * @param registry registry represents a single or multiple Quobyte Registry services specified as a
 *   string as host:port pair (multiple entries are separated with commas) which acts as the central
 *   registry for volumes
 * @param tenant tenant owning the given Quobyte volume in the Backend Used with dynamically
 *   provisioned Quobyte volumes, value is set by the plugin
 * @param user user to map volume access to Defaults to serivceaccount user
 * @param volume volume is a string that references an already created Quobyte volume by name.
 */
@Serializable
public data class QuobyteVolumeSource(
    public val readOnly: Boolean,
    public val registry: String,
    public val tenant: String,
    public val user: String,
    public val volume: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "QuobyteVolumeSource"
}
