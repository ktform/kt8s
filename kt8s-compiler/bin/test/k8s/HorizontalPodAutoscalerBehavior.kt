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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param scaleDown scaleDown is scaling policy for scaling Down. If not set, the default value is
 *   to allow to scale down to minReplicas pods, with a 300 second stabilization window (i.e., the
 *   highest recommendation for the last 300sec is used).
 * @param scaleUp scaleUp is scaling policy for scaling Up. If not set, the default value is the
 *   higher of:
 *     * increase no more than 4 pods per 60 seconds
 *     * double the number of pods per 60 seconds No stabilization is used.
 */
@Serializable
public data class HorizontalPodAutoscalerBehavior(
    public val scaleDown: HPAScalingRules,
    public val scaleUp: HPAScalingRules,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v2"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v2"

    @SerialName("kind") override val kind: String = "HorizontalPodAutoscalerBehavior"
}
