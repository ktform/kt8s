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
 * @param averageUtilization currentAverageUtilization is the current value of the average of the
 *   resource metric across all relevant pods, represented as a percentage of the requested value of
 *   the resource for the pods.
 * @param averageValue averageValue is the current value of the average of the metric across all
 *   relevant pods (as a quantity)
 * @param value value is the current value of the metric (as a quantity).
 */
@Serializable
public data class MetricValueStatus(
    public val averageUtilization: Int,
    public val averageValue: StringOrNumber,
    public val `value`: StringOrNumber,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v2"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v2"

    @SerialName("kind") override val kind: String = "MetricValueStatus"
}
