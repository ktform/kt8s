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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param currentCPUUtilizationPercentage currentCPUUtilizationPercentage is the current average CPU
 *   utilization over all pods, represented as a percentage of requested CPU, e.g. 70 means that an
 *   average pod is using now 70% of its requested CPU.
 * @param currentReplicas currentReplicas is the current number of replicas of pods managed by this
 *   autoscaler.
 * @param desiredReplicas desiredReplicas is the desired number of replicas of pods managed by this
 *   autoscaler.
 * @param lastScaleTime lastScaleTime is the last time the HorizontalPodAutoscaler scaled the number
 *   of pods; used by the autoscaler to control how often the number of pods is changed.
 * @param observedGeneration observedGeneration is the most recent generation observed by this
 *   autoscaler.
 */
@Serializable
public data class HorizontalPodAutoscalerStatus(
    public val currentCPUUtilizationPercentage: Int,
    public val currentReplicas: Int,
    public val desiredReplicas: Int,
    public val lastScaleTime: KubernetesTime,
    public val observedGeneration: Long,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v1"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "HorizontalPodAutoscalerStatus"
}
