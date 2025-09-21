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
 * @param allocatable allocatable represents the volume resources of a node that are available for
 *   scheduling. This field is beta.
 * @param name name represents the name of the CSI driver that this object refers to. This MUST be
 *   the same name returned by the CSI GetPluginName() call for that driver.
 * @param nodeID nodeID of the node from the driver point of view. This field enables Kubernetes to
 *   communicate with storage systems that do not share the same nomenclature for nodes. For
 *   example, Kubernetes may refer to a given node as "node1", but the storage system may refer to
 *   the same node as "nodeA". When Kubernetes issues a command to the storage system to attach a
 *   volume to a specific node, it can use this field to refer to the node name using the ID that
 *   the storage system will understand, e.g. "nodeA" instead of "node1". This field is required.
 * @param topologyKeys topologyKeys is the list of keys supported by the driver. When a driver is
 *   initialized on a cluster, it provides a set of topology keys that it understands (e.g.
 *   "company.com/zone", "company.com/region"). When a driver is initialized on a node, it provides
 *   the same topology keys along with values. Kubelet will expose these topology keys as labels on
 *   its own node object. When Kubernetes does topology aware provisioning, it can use this list to
 *   determine which labels it should retrieve from the node object and pass back to the driver. It
 *   is possible for different nodes to use different topology keys. This can be empty if driver
 *   does not support topology.
 */
@Serializable
public data class CSINodeDriver(
    public val allocatable: VolumeNodeResources,
    public val name: String,
    public val nodeID: String,
    public val topologyKeys: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.storage/v1"

    @Transient override val group: String = "io.k8s.api.storage"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CSINodeDriver"
}
