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
 * @param status Specifies the required Pod condition status. To match a pod condition it is
 *   required that the specified status equals the pod condition status. Defaults to True.
 * @param type Specifies the required Pod condition type. To match a pod condition it is required
 *   that specified type equals the pod condition type.
 */
@Serializable
public data class PodFailurePolicyOnPodConditionsPattern(
    public val status: String,
    public val type: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.batch/v1"

    @Transient override val group: String = "io.k8s.api.batch"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodFailurePolicyOnPodConditionsPattern"
}
