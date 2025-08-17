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
 * @param hard hard is the set of desired hard limits for each named resource. More info:
 *   https://kubernetes.io/docs/concepts/policy/resource-quotas/
 * @param scopeSelector scopeSelector is also a collection of filters like scopes that must match
 *   each object tracked by a quota but expressed using ScopeSelectorOperator in combination with
 *   possible values. For a resource to match, both scopes AND scopeSelector (if specified in spec),
 *   must be matched.
 * @param scopes A collection of filters that must match each object tracked by a quota. If not
 *   specified, the quota matches all objects.
 */
@Serializable
public data class ResourceQuotaSpec(
    public val hard: StringOrNumber,
    public val scopeSelector: ScopeSelector,
    public val scopes: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ResourceQuotaSpec"
}
