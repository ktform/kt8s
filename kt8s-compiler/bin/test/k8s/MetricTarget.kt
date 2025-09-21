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
 * @param averageUtilization averageUtilization is the target value of the average of the resource
 *   metric across all relevant pods, represented as a percentage of the requested value of the
 *   resource for the pods. Currently only valid for Resource metric source type
 * @param averageValue averageValue is the target value of the average of the metric across all
 *   relevant pods (as a quantity)
 * @param type type represents whether the metric type is Utilization, Value, or AverageValue
 * @param value value is the target value of the metric (as a quantity).
 */
@Serializable
public data class MetricTarget(
    public val averageUtilization: Int,
    public val averageValue: StringOrNumber,
    public val type: String,
    public val `value`: StringOrNumber,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v2"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v2"

    @SerialName("kind") override val kind: String = "MetricTarget"
}
