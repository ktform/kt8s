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
 * @param rawSelector rawSelector is the serialization of a field selector that would be included in
 *   a query parameter. Webhook implementations are encouraged to ignore rawSelector. The
 *   kube-apiserver's *SubjectAccessReview will parse the rawSelector as long as the requirements
 *   are not present.
 * @param requirements requirements is the parsed interpretation of a label selector. All
 *   requirements must be met for a resource instance to match the selector. Webhook implementations
 *   should handle requirements, but how to handle them is up to the webhook. Since requirements can
 *   only limit the request, it is safe to authorize as unlimited request if the requirements are
 *   not understood.
 */
@Serializable
public data class LabelSelectorAttributes(
    public val rawSelector: String,
    public val requirements: List<LabelSelectorRequirement>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.authorization/v1"

    @Transient override val group: String = "io.k8s.api.authorization"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "LabelSelectorAttributes"
}
