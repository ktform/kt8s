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
 * @param name Name is the name of the operating system. The currently supported values are linux
 *   and windows. Additional value may be defined in future and can be one of:
 *   https://github.com/opencontainers/runtime-spec/blob/master/config.md#platform-specific-configuration
 *   Clients should expect to handle additional values and treat unrecognized values in this field
 *   as os: null
 */
@Serializable
public data class PodOS(public val name: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodOS"
}
