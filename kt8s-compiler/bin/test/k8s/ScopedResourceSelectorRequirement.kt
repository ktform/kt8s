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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param operator Represents a scope's relationship to a set of values. Valid operators are In,
 *   NotIn, Exists, DoesNotExist.
 * @param scopeName The name of the scope that the selector applies to.
 * @param values An array of string values. If the operator is In or NotIn, the values array must be
 *   non-empty. If the operator is Exists or DoesNotExist, the values array must be empty. This
 *   array is replaced during a strategic merge patch.
 */
@Serializable
public data class ScopedResourceSelectorRequirement(
    public val `operator`: String,
    public val scopeName: String,
    public val values: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ScopedResourceSelectorRequirement"
}
