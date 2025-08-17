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
 * @param periodSeconds periodSeconds specifies the window of time for which the policy should hold
 *   true. PeriodSeconds must be greater than zero and less than or equal to 1800 (30 min).
 * @param type type is used to specify the scaling policy.
 * @param value value contains the amount of change which is permitted by the policy. It must be
 *   greater than zero
 */
@Serializable
public data class HPAScalingPolicy(
    public val periodSeconds: Int,
    public val type: String,
    public val `value`: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v2"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v2"

    @SerialName("kind") override val kind: String = "HPAScalingPolicy"
}
