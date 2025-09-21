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
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param maxReplicas maxReplicas is the upper limit for the number of pods that can be set by the
 *   autoscaler; cannot be smaller than MinReplicas.
 * @param minReplicas minReplicas is the lower limit for the number of replicas to which the
 *   autoscaler can scale down. It defaults to 1 pod. minReplicas is allowed to be 0 if the alpha
 *   feature gate HPAScaleToZero is enabled and at least one Object or External metric is
 *   configured. Scaling is active as long as at least one metric value is available.
 * @param scaleTargetRef reference to scaled resource; horizontal pod autoscaler will learn the
 *   current resource consumption and will set the desired number of pods by using its Scale
 *   subresource.
 * @param targetCPUUtilizationPercentage targetCPUUtilizationPercentage is the target average CPU
 *   utilization (represented as a percentage of requested CPU) over all the pods; if not specified
 *   the default autoscaling policy will be used.
 */
@Serializable
public data class HorizontalPodAutoscalerSpec(
    public val maxReplicas: Int,
    public val minReplicas: Int,
    public val scaleTargetRef: CrossVersionObjectReference,
    public val targetCPUUtilizationPercentage: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v1"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "HorizontalPodAutoscalerSpec"
}
