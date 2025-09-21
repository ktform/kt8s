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
 * @param key key is the field selector key that the requirement applies to.
 * @param operator operator represents a key's relationship to a set of values. Valid operators are
 *   In, NotIn, Exists, DoesNotExist. The list of operators may grow in the future.
 * @param values values is an array of string values. If the operator is In or NotIn, the values
 *   array must be non-empty. If the operator is Exists or DoesNotExist, the values array must be
 *   empty.
 */
@Serializable
public data class FieldSelectorRequirement(
    public val key: String,
    public val `operator`: String,
    public val values: List<String>,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

    @Transient override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "FieldSelectorRequirement"
}
