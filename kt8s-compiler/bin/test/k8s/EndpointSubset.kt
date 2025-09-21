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
 * @param addresses IP addresses which offer the related ports that are marked as ready. These
 *   endpoints should be considered safe for load balancers and clients to utilize.
 * @param notReadyAddresses IP addresses which offer the related ports but are not currently marked
 *   as ready because they have not yet finished starting, have recently failed a readiness check,
 *   or have recently failed a liveness check.
 * @param ports Port numbers available on the related IP addresses.
 */
@Serializable
public data class EndpointSubset(
    public val addresses: List<EndpointAddress>,
    public val notReadyAddresses: List<EndpointAddress>,
    public val ports: List<EndpointPort>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "EndpointSubset"
}
