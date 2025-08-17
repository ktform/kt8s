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
 * @param replicas Replicas is the number of desired replicas. This is a pointer to distinguish
 *   between explicit zero and unspecified. Defaults to 1. More info:
 *   https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller#what-is-a-replicationcontroller
 * @param selector Selector is a label query over pods that should match the Replicas count. If
 *   Selector is empty, it is defaulted to the labels present on the Pod template. Label keys and
 *   values that must match in order to be controlled by this replication controller, if empty
 *   defaulted to labels on Pod template. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/#label-selectors
 * @param template Template is the object that describes the pod that will be created if
 *   insufficient replicas are detected. This takes precedence over a TemplateRef. The only allowed
 *   template.spec.restartPolicy value is "Always". More info:
 *   https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller#pod-template
 */
@Serializable
public data class ReplicationControllerSpec(
    public val minReadySeconds: Int,
    public val replicas: Int,
    public val selector: RawJsonObject,
    public val template: PodTemplateSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ReplicationControllerSpec"
}
