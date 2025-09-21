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
 * @param maxUnavailable The maximum number of pods that can be unavailable during the update. Value
 *   can be an absolute number (ex: 5) or a percentage of desired pods (ex: 10%). Absolute number is
 *   calculated from percentage by rounding up. This can not be 0. Defaults to 1. This field is
 *   alpha-level and is only honored by servers that enable the MaxUnavailableStatefulSet feature.
 *   The field applies to all pods in the range 0 to Replicas-1. That means if there is any
 *   unavailable pod in the range 0 to Replicas-1, it will be counted towards MaxUnavailable.
 * @param partition Partition indicates the ordinal at which the StatefulSet should be partitioned
 *   for updates. During a rolling update, all pods from ordinal Replicas-1 to Partition are
 *   updated. All pods from ordinal Partition-1 to 0 remain untouched. This is helpful in being able
 *   to do a canary based deployment. The default value is 0.
 */
@Serializable
public data class RollingUpdateStatefulSetStrategy(
    public val maxUnavailable: IntOrString,
    public val partition: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "RollingUpdateStatefulSetStrategy"
}
