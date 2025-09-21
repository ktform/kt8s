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
 * @param name Name of the resource. Must be unique within the pod and in case of non-DRA resource,
 *   match one of the resources from the pod spec. For DRA resources, the value must be
 *   "claim:<claim_name>/<request>". When this status is reported about a container, the
 *   "claim_name" and "request" must match one of the claims of this container.
 * @param resources List of unique resources health. Each element in the list contains an unique
 *   resource ID and its health. At a minimum, for the lifetime of a Pod, resource ID must uniquely
 *   identify the resource allocated to the Pod on the Node. If other Pod on the same Node reports
 *   the status with the same resource ID, it must be the same resource they share. See ResourceID
 *   type definition for a specific format it has in various use cases.
 */
@Serializable
public data class ResourceStatus(
    public val name: String,
    public val resources: List<ResourceHealth>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ResourceStatus"
}
