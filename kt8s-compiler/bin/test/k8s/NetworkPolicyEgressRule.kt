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
 * @param ports ports is a list of destination ports for outgoing traffic. Each item in this list is
 *   combined using a logical OR. If this field is empty or missing, this rule matches all ports
 *   (traffic not restricted by port). If this field is present and contains at least one item, then
 *   this rule allows traffic only if the traffic matches at least one port in the list.
 * @param to to is a list of destinations for outgoing traffic of pods selected for this rule. Items
 *   in this list are combined using a logical OR operation. If this field is empty or missing, this
 *   rule matches all destinations (traffic not restricted by destination). If this field is present
 *   and contains at least one item, this rule allows traffic only if the traffic matches at least
 *   one item in the to list.
 */
@Serializable
public data class NetworkPolicyEgressRule(
    public val ports: List<NetworkPolicyPort>,
    public val to: List<NetworkPolicyPeer>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NetworkPolicyEgressRule"
}
