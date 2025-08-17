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

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param configSource Deprecated: Previously used to specify the source of the node's configuration
 *   for the DynamicKubeletConfig feature. This feature is removed.
 * @param externalID Deprecated. Not all kubelets will set this field. Remove field after 1.13. see:
 *   https://issues.k8s.io/61966
 * @param podCIDR PodCIDR represents the pod IP range assigned to the node.
 * @param podCIDRs podCIDRs represents the IP ranges assigned to the node for usage by Pods on that
 *   node. If this field is specified, the 0th entry must match the podCIDR field. It may contain at
 *   most 1 value for each of IPv4 and IPv6.
 * @param providerID ID of the node assigned by the cloud provider in the format:
 *   <ProviderName>://<ProviderSpecificNodeID>
 * @param taints If specified, the node's taints.
 * @param unschedulable Unschedulable controls node schedulability of new pods. By default, node is
 *   schedulable. More info:
 *   https://kubernetes.io/docs/concepts/nodes/node/#manual-node-administration
 */
@Serializable
public data class NodeSpec(
    public val configSource: NodeConfigSource,
    public val externalID: String,
    public val podCIDR: String,
    public val podCIDRs: List<String>,
    public val providerID: String,
    public val taints: List<Taint>,
    public val unschedulable: Boolean,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NodeSpec"
}
