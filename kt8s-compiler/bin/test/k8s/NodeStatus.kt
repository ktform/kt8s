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
 * @param addresses List of addresses reachable to the node. Queried from cloud provider, if
 *   available. More info: https://kubernetes.io/docs/reference/node/node-status/#addresses Note:
 *   This field is declared as mergeable, but the merge key is not sufficiently unique, which can
 *   cause data corruption when it is merged. Callers should instead use a full-replacement patch.
 *   See https://pr.k8s.io/79391 for an example. Consumers should assume that addresses can change
 *   during the lifetime of a Node. However, there are some exceptions where this may not be
 *   possible, such as Pods that inherit a Node's address in its own status or consumers of the
 *   downward API (status.hostIP).
 * @param allocatable Allocatable represents the resources of a node that are available for
 *   scheduling. Defaults to Capacity.
 * @param capacity Capacity represents the total resources of a node. More info:
 *   https://kubernetes.io/docs/reference/node/node-status/#capacity
 * @param conditions Conditions is an array of current observed node conditions. More info:
 *   https://kubernetes.io/docs/reference/node/node-status/#condition
 * @param config Status of the config assigned to the node via the dynamic Kubelet config feature.
 * @param daemonEndpoints Endpoints of daemons running on the Node.
 * @param features Features describes the set of features implemented by the CRI implementation.
 * @param images List of container images on this node
 * @param nodeInfo Set of ids/uuids to uniquely identify the node. More info:
 *   https://kubernetes.io/docs/reference/node/node-status/#info
 * @param phase NodePhase is the recently observed lifecycle phase of the node. More info:
 *   https://kubernetes.io/docs/concepts/nodes/node/#phase The field is never populated, and now is
 *   deprecated.
 * @param runtimeHandlers The available runtime handlers.
 * @param volumesAttached List of volumes that are attached to the node.
 * @param volumesInUse List of attachable volumes in use (mounted) by the node.
 */
@Serializable
public data class NodeStatus(
    public val addresses: List<NodeAddress>,
    public val allocatable: StringOrNumber,
    public val capacity: StringOrNumber,
    public val conditions: List<NodeCondition>,
    public val config: NodeConfigStatus,
    public val daemonEndpoints: NodeDaemonEndpoints,
    public val features: NodeFeatures,
    public val images: List<ContainerImage>,
    public val nodeInfo: NodeSystemInfo,
    public val phase: String,
    public val runtimeHandlers: List<NodeRuntimeHandler>,
    public val volumesAttached: List<AttachedVolume>,
    public val volumesInUse: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NodeStatus"
}
