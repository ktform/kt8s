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
 * @param preferredDuringSchedulingIgnoredDuringExecution The scheduler will prefer to schedule pods
 *   to nodes that satisfy the affinity expressions specified by this field, but it may choose a
 *   node that violates one or more of the expressions. The node that is most preferred is the one
 *   with the greatest sum of weights, i.e. for each node that meets all of the scheduling
 *   requirements (resource request, requiredDuringScheduling affinity expressions, etc.), compute a
 *   sum by iterating through the elements of this field and adding "weight" to the sum if the node
 *   has pods which matches the corresponding podAffinityTerm; the node(s) with the highest sum are
 *   the most preferred.
 * @param requiredDuringSchedulingIgnoredDuringExecution If the affinity requirements specified by
 *   this field are not met at scheduling time, the pod will not be scheduled onto the node. If the
 *   affinity requirements specified by this field cease to be met at some point during pod
 *   execution (e.g. due to a pod label update), the system may or may not try to eventually evict
 *   the pod from its node. When there are multiple elements, the lists of nodes corresponding to
 *   each podAffinityTerm are intersected, i.e. all terms must be satisfied.
 */
@Serializable
public data class PodAffinity(
    public val preferredDuringSchedulingIgnoredDuringExecution: List<WeightedPodAffinityTerm>,
    public val requiredDuringSchedulingIgnoredDuringExecution: List<PodAffinityTerm>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodAffinity"
}
