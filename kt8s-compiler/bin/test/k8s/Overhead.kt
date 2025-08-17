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
 * @param podFixed podFixed represents the fixed resource overhead associated with running a pod.
 */
@Serializable
public data class Overhead(public val podFixed: StringOrNumber) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.node/v1"

    @Transient override val group: String = "io.k8s.api.node"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Overhead"
}
