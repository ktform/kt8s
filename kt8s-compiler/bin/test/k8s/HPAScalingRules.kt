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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param policies policies is a list of potential scaling polices which can be used during scaling.
 *   If not set, use the default values: - For scale up: allow doubling the number of pods, or an
 *   absolute change of 4 pods in a 15s window. - For scale down: allow all pods to be removed in a
 *   15s window.
 * @param selectPolicy selectPolicy is used to specify which policy should be used. If not set, the
 *   default value Max is used.
 * @param stabilizationWindowSeconds stabilizationWindowSeconds is the number of seconds for which
 *   past recommendations should be considered while scaling up or scaling down.
 *   StabilizationWindowSeconds must be greater than or equal to zero and less than or equal to 3600
 *   (one hour). If not set, use the default values: - For scale up: 0 (i.e. no stabilization is
 *   done). - For scale down: 300 (i.e. the stabilization window is 300 seconds long).
 * @param tolerance tolerance is the tolerance on the ratio between the current and desired metric
 *   value under which no updates are made to the desired number of replicas (e.g. 0.01 for 1%).
 *   Must be greater than or equal to zero. If not set, the default cluster-wide tolerance is
 *   applied (by default 10%).
 *
 * For example, if autoscaling is configured with a memory consumption target of 100Mi, and
 * scale-down and scale-up tolerances of 5% and 1% respectively, scaling will be triggered when the
 * actual consumption falls below 95Mi or exceeds 101Mi.
 *
 * This is an alpha field and requires enabling the HPAConfigurableTolerance feature gate.
 */
@Serializable
public data class HPAScalingRules(
    public val policies: List<HPAScalingPolicy>,
    public val selectPolicy: String,
    public val stabilizationWindowSeconds: Int,
    public val tolerance: StringOrNumber,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v2"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v2"

    @SerialName("kind") override val kind: String = "HPAScalingRules"
}
