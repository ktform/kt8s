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
 * @param lastProbeTime lastProbeTime is the time we probed the condition.
 * @param lastTransitionTime lastTransitionTime is the time the condition transitioned from one
 *   status to another.
 * @param message message is the human-readable message indicating details about last transition.
 * @param reason reason is a unique, this should be a short, machine understandable string that
 *   gives the reason for condition's last transition. If it reports "Resizing" that means the
 *   underlying persistent volume is being resized.
 * @param status Status is the status of the condition. Can be True, False, Unknown. More info:
 *   https://kubernetes.io/docs/reference/kubernetes-api/config-and-storage-resources/persistent-volume-claim-v1/#:~:text=state%20of%20pvc-,conditions.status,-(string)%2C%20required
 * @param type Type is the type of the condition. More info:
 *   https://kubernetes.io/docs/reference/kubernetes-api/config-and-storage-resources/persistent-volume-claim-v1/#:~:text=set%20to%20%27ResizeStarted%27.-,PersistentVolumeClaimCondition,-contains%20details%20about
 */
@Serializable
public data class PersistentVolumeClaimCondition(
    public val lastProbeTime: KubernetesTime,
    public val lastTransitionTime: KubernetesTime,
    public val message: String,
    public val reason: String,
    public val status: String,
    public val type: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PersistentVolumeClaimCondition"
}
