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
 * @param maxSurge The maximum number of pods that can be scheduled above the desired number of
 *   pods. Value can be an absolute number (ex: 5) or a percentage of desired pods (ex: 10%). This
 *   can not be 0 if MaxUnavailable is 0. Absolute number is calculated from percentage by rounding
 *   up. Defaults to 25%. Example: when this is set to 30%, the new ReplicaSet can be scaled up
 *   immediately when the rolling update starts, such that the total number of old and new pods do
 *   not exceed 130% of desired pods. Once old pods have been killed, new ReplicaSet can be scaled
 *   up further, ensuring that total number of pods running at any time during the update is at most
 *   130% of desired pods.
 * @param maxUnavailable The maximum number of pods that can be unavailable during the update. Value
 *   can be an absolute number (ex: 5) or a percentage of desired pods (ex: 10%). Absolute number is
 *   calculated from percentage by rounding down. This can not be 0 if MaxSurge is 0. Defaults to
 *   25%. Example: when this is set to 30%, the old ReplicaSet can be scaled down to 70% of desired
 *   pods immediately when the rolling update starts. Once new pods are ready, old ReplicaSet can be
 *   scaled down further, followed by scaling up the new ReplicaSet, ensuring that the total number
 *   of pods available at all times during the update is at least 70% of desired pods.
 */
@Serializable
public data class RollingUpdateDeployment(
    public val maxSurge: IntOrString,
    public val maxUnavailable: IntOrString,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "RollingUpdateDeployment"
}
