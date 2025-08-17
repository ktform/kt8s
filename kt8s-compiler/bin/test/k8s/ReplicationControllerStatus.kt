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

import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param availableReplicas The number of available replicas (ready for at least minReadySeconds)
 *   for this replication controller.
 * @param conditions Represents the latest available observations of a replication controller's
 *   current state.
 * @param fullyLabeledReplicas The number of pods that have labels matching the labels of the pod
 *   template of the replication controller.
 * @param observedGeneration ObservedGeneration reflects the generation of the most recently
 *   observed replication controller.
 * @param readyReplicas The number of ready replicas for this replication controller.
 * @param replicas Replicas is the most recently observed number of replicas. More info:
 *   https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller#what-is-a-replicationcontroller
 */
@Serializable
public data class ReplicationControllerStatus(
    public val availableReplicas: Int,
    public val conditions: List<ReplicationControllerCondition>,
    public val fullyLabeledReplicas: Int,
    public val observedGeneration: Long,
    public val readyReplicas: Int,
    public val replicas: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ReplicationControllerStatus"
}
