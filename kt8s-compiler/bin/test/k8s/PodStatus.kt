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

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param conditions Current service state of pod. More info:
 *   https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#pod-conditions
 * @param containerStatuses Statuses of containers in this pod. Each container in the pod should
 *   have at most one status in this list, and all statuses should be for containers in the pod.
 *   However this is not enforced. If a status for a non-existent container is present in the list,
 *   or the list has duplicate names, the behavior of various Kubernetes components is not defined
 *   and those statuses might be ignored. More info:
 *   https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#pod-and-container-status
 * @param ephemeralContainerStatuses Statuses for any ephemeral containers that have run in this
 *   pod. Each ephemeral container in the pod should have at most one status in this list, and all
 *   statuses should be for containers in the pod. However this is not enforced. If a status for a
 *   non-existent container is present in the list, or the list has duplicate names, the behavior of
 *   various Kubernetes components is not defined and those statuses might be ignored. More info:
 *   https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#pod-and-container-status
 * @param hostIP hostIP holds the IP address of the host to which the pod is assigned. Empty if the
 *   pod has not started yet. A pod can be assigned to a node that has a problem in kubelet which in
 *   turns mean that HostIP will not be updated even if there is a node is assigned to pod
 * @param hostIPs hostIPs holds the IP addresses allocated to the host. If this field is specified,
 *   the first entry must match the hostIP field. This list is empty if the pod has not started yet.
 *   A pod can be assigned to a node that has a problem in kubelet which in turns means that HostIPs
 *   will not be updated even if there is a node is assigned to this pod.
 * @param initContainerStatuses Statuses of init containers in this pod. The most recent successful
 *   non-restartable init container will have ready = true, the most recently started container will
 *   have startTime set. Each init container in the pod should have at most one status in this list,
 *   and all statuses should be for containers in the pod. However this is not enforced. If a status
 *   for a non-existent container is present in the list, or the list has duplicate names, the
 *   behavior of various Kubernetes components is not defined and those statuses might be ignored.
 *   More info:
 *   https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle/#pod-and-container-status
 * @param message A human readable message indicating details about why the pod is in this
 *   condition.
 * @param nominatedNodeName nominatedNodeName is set only when this pod preempts other pods on the
 *   node, but it cannot be scheduled right away as preemption victims receive their graceful
 *   termination periods. This field does not guarantee that the pod will be scheduled on this node.
 *   Scheduler may decide to place the pod elsewhere if other nodes become available sooner.
 *   Scheduler may also decide to give the resources on this node to a higher priority pod that is
 *   created after preemption. As a result, this field may be different than PodSpec.nodeName when
 *   the pod is scheduled.
 * @param observedGeneration If set, this represents the .metadata.generation that the pod status
 *   was set based upon. This is an alpha field. Enable PodObservedGenerationTracking to be able to
 *   use this field.
 * @param phase The phase of a Pod is a simple, high-level summary of where the Pod is in its
 *   lifecycle. The conditions array, the reason and message fields, and the individual container
 *   status arrays contain more detail about the pod's status. There are five possible phase values:
 *
 * Pending: The pod has been accepted by the Kubernetes system, but one or more of the container
 * images has not been created. This includes time before being scheduled as well as time spent
 * downloading images over the network, which could take a while. Running: The pod has been bound to
 * a node, and all of the containers have been created. At least one container is still running, or
 * is in the process of starting or restarting. Succeeded: All containers in the pod have terminated
 * in success, and will not be restarted. Failed: All containers in the pod have terminated, and at
 * least one container has terminated in failure. The container either exited with non-zero status
 * or was terminated by the system. Unknown: For some reason the state of the pod could not be
 * obtained, typically due to an error in communicating with the host of the pod.
 *
 * More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#pod-phase
 *
 * @param podIP podIP address allocated to the pod. Routable at least within the cluster. Empty if
 *   not yet allocated.
 * @param podIPs podIPs holds the IP addresses allocated to the pod. If this field is specified, the
 *   0th entry must match the podIP field. Pods may be allocated at most 1 value for each of IPv4
 *   and IPv6. This list is empty if no IPs have been allocated yet.
 * @param qosClass The Quality of Service (QOS) classification assigned to the pod based on resource
 *   requirements See PodQOSClass type for available QOS classes More info:
 *   https://kubernetes.io/docs/concepts/workloads/pods/pod-qos/#quality-of-service-classes
 * @param reason A brief CamelCase message indicating details about why the pod is in this state.
 *   e.g. 'Evicted'
 * @param resize Status of resources resize desired for pod's containers. It is empty if no
 *   resources resize is pending. Any changes to container resources will automatically set this to
 *   "Proposed" Deprecated: Resize status is moved to two pod conditions PodResizePending and
 *   PodResizeInProgress. PodResizePending will track states where the spec has been resized, but
 *   the Kubelet has not yet allocated the resources. PodResizeInProgress will track in-progress
 *   resizes, and should be present whenever allocated resources != acknowledged resources.
 * @param resourceClaimStatuses Status of resource claims.
 * @param startTime RFC 3339 date and time at which the object was acknowledged by the Kubelet. This
 *   is before the Kubelet pulled the container image(s) for the pod.
 */
@Serializable
public data class PodStatus(
    public val conditions: List<PodCondition>,
    public val containerStatuses: List<ContainerStatus>,
    public val ephemeralContainerStatuses: List<ContainerStatus>,
    public val hostIP: String,
    public val hostIPs: List<HostIP>,
    public val initContainerStatuses: List<ContainerStatus>,
    public val message: String,
    public val nominatedNodeName: String,
    public val observedGeneration: Long,
    public val phase: String,
    public val podIP: String,
    public val podIPs: List<PodIP>,
    public val qosClass: String,
    public val reason: String,
    public val resize: String,
    public val resourceClaimStatuses: List<PodResourceClaimStatus>,
    public val startTime: KubernetesTime,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodStatus"
}
