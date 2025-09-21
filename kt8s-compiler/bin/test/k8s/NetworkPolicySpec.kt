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
 * @param egress egress is a list of egress rules to be applied to the selected pods. Outgoing
 *   traffic is allowed if there are no NetworkPolicies selecting the pod (and cluster policy
 *   otherwise allows the traffic), OR if the traffic matches at least one egress rule across all of
 *   the NetworkPolicy objects whose podSelector matches the pod. If this field is empty then this
 *   NetworkPolicy limits all outgoing traffic (and serves solely to ensure that the pods it selects
 *   are isolated by default). This field is beta-level in 1.8
 * @param ingress ingress is a list of ingress rules to be applied to the selected pods. Traffic is
 *   allowed to a pod if there are no NetworkPolicies selecting the pod (and cluster policy
 *   otherwise allows the traffic), OR if the traffic source is the pod's local node, OR if the
 *   traffic matches at least one ingress rule across all of the NetworkPolicy objects whose
 *   podSelector matches the pod. If this field is empty then this NetworkPolicy does not allow any
 *   traffic (and serves solely to ensure that the pods it selects are isolated by default)
 * @param podSelector podSelector selects the pods to which this NetworkPolicy object applies. The
 *   array of ingress rules is applied to any pods selected by this field. Multiple network policies
 *   can select the same set of pods. In this case, the ingress rules for each are combined
 *   additively. This field is NOT optional and follows standard label selector semantics. An empty
 *   podSelector matches all pods in this namespace.
 * @param policyTypes policyTypes is a list of rule types that the NetworkPolicy relates to. Valid
 *   options are ["Ingress"], ["Egress"], or ["Ingress", "Egress"]. If this field is not specified,
 *   it will default based on the existence of ingress or egress rules; policies that contain an
 *   egress section are assumed to affect egress, and all policies (whether or not they contain an
 *   ingress section) are assumed to affect ingress. If you want to write an egress-only policy, you
 *   must explicitly specify policyTypes [ "Egress" ]. Likewise, if you want to write a policy that
 *   specifies that no egress is allowed, you must specify a policyTypes value that include "Egress"
 *   (since such a policy would not include an egress section and would otherwise default to just
 *   [ "Ingress" ]). This field is beta-level in 1.8
 */
@Serializable
public data class NetworkPolicySpec(
    public val egress: List<NetworkPolicyEgressRule>,
    public val ingress: List<NetworkPolicyIngressRule>,
    public val podSelector: LabelSelector,
    public val policyTypes: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NetworkPolicySpec"
}
