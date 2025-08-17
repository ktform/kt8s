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
 * @param availableReplicas Total number of available pods (ready for at least minReadySeconds)
 *   targeted by this statefulset.
 * @param collisionCount collisionCount is the count of hash collisions for the StatefulSet. The
 *   StatefulSet controller uses this field as a collision avoidance mechanism when it needs to
 *   create the name for the newest ControllerRevision.
 * @param conditions Represents the latest available observations of a statefulset's current state.
 * @param currentReplicas currentReplicas is the number of Pods created by the StatefulSet
 *   controller from the StatefulSet version indicated by currentRevision.
 * @param currentRevision currentRevision, if not empty, indicates the version of the StatefulSet
 *   used to generate Pods in the sequence [0,currentReplicas).
 * @param observedGeneration observedGeneration is the most recent generation observed for this
 *   StatefulSet. It corresponds to the StatefulSet's generation, which is updated on mutation by
 *   the API Server.
 * @param readyReplicas readyReplicas is the number of pods created for this StatefulSet with a
 *   Ready Condition.
 * @param replicas replicas is the number of Pods created by the StatefulSet controller.
 * @param updateRevision updateRevision, if not empty, indicates the version of the StatefulSet used
 *   to generate Pods in the sequence [replicas-updatedReplicas,replicas)
 * @param updatedReplicas updatedReplicas is the number of Pods created by the StatefulSet
 *   controller from the StatefulSet version indicated by updateRevision.
 */
@Serializable
public data class StatefulSetStatus(
    public val availableReplicas: Int,
    public val collisionCount: Int,
    public val conditions: List<StatefulSetCondition>,
    public val currentReplicas: Int,
    public val currentRevision: String,
    public val observedGeneration: Long,
    public val readyReplicas: Int,
    public val replicas: Int,
    public val updateRevision: String,
    public val updatedReplicas: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "StatefulSetStatus"
}
