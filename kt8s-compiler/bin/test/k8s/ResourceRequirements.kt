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
 * @param claims Claims lists the names of resources, defined in spec.resourceClaims, that are used
 *   by this container.
 *
 * This is an alpha field and requires enabling the DynamicResourceAllocation feature gate.
 *
 * This field is immutable. It can only be set for containers.
 *
 * @param limits Limits describes the maximum amount of compute resources allowed. More info:
 *   https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/
 * @param requests Requests describes the minimum amount of compute resources required. If Requests
 *   is omitted for a container, it defaults to Limits if that is explicitly specified, otherwise to
 *   an implementation-defined value. Requests cannot exceed Limits. More info:
 *   https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/
 */
@Serializable
public data class ResourceRequirements(
    public val claims: List<ResourceClaim>,
    public val limits: StringOrNumber,
    public val requests: StringOrNumber,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ResourceRequirements"
}
