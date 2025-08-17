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
 * @param from from is a list of sources which should be able to access the pods selected for this
 *   rule. Items in this list are combined using a logical OR operation. If this field is empty or
 *   missing, this rule matches all sources (traffic not restricted by source). If this field is
 *   present and contains at least one item, this rule allows traffic only if the traffic matches at
 *   least one item in the from list.
 * @param ports ports is a list of ports which should be made accessible on the pods selected for
 *   this rule. Each item in this list is combined using a logical OR. If this field is empty or
 *   missing, this rule matches all ports (traffic not restricted by port). If this field is present
 *   and contains at least one item, then this rule allows traffic only if the traffic matches at
 *   least one port in the list.
 */
@Serializable
public data class NetworkPolicyIngressRule(
    public val from: List<NetworkPolicyPeer>,
    public val ports: List<NetworkPolicyPort>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NetworkPolicyIngressRule"
}
