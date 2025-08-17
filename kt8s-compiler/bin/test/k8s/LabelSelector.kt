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
 * @param matchExpressions matchExpressions is a list of label selector requirements. The
 *   requirements are ANDed.
 * @param matchLabels matchLabels is a map of {key,value} pairs. A single {key,value} in the
 *   matchLabels map is equivalent to an element of matchExpressions, whose key field is "key", the
 *   operator is "In", and the values array contains only "value". The requirements are ANDed.
 */
@Serializable
public data class LabelSelector(
    public val matchExpressions: List<LabelSelectorRequirement>,
    public val matchLabels: RawJsonObject,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

    @Transient override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "LabelSelector"
}
