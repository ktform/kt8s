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
 * @param hostname hostname is set for load-balancer ingress points that are DNS based.
 * @param ip ip is set for load-balancer ingress points that are IP based.
 * @param ports ports provides information about the ports exposed by this LoadBalancer.
 */
@Serializable
public data class IngressLoadBalancerIngress(
    public val hostname: String,
    public val ip: String,
    public val ports: List<IngressPortStatus>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "IngressLoadBalancerIngress"
}
