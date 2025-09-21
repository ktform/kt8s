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
 * @param minReadySeconds Minimum number of seconds for which a newly created pod should be ready
 *   without any of its container crashing, for it to be considered available. Defaults to 0 (pod
 *   will be considered available as soon as it is ready)
 * @param replicas Replicas is the number of desired pods. This is a pointer to distinguish between
 *   explicit zero and unspecified. Defaults to 1. More info:
 *   https://kubernetes.io/docs/concepts/workloads/controllers/replicaset
 * @param selector Selector is a label query over pods that should match the replica count. Label
 *   keys and values that must match in order to be controlled by this replica set. It must match
 *   the pod template's labels. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/#label-selectors
 * @param template Template is the object that describes the pod that will be created if
 *   insufficient replicas are detected. More info:
 *   https://kubernetes.io/docs/concepts/workloads/controllers/replicaset/#pod-template
 */
@Serializable
public data class ReplicaSetSpec(
    public val minReadySeconds: Int,
    public val replicas: Int,
    public val selector: LabelSelector,
    public val template: PodTemplateSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ReplicaSetSpec"
}
