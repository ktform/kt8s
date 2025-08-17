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

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param minReadySeconds Minimum number of seconds for which a newly created pod should be ready
 *   without any of its container crashing, for it to be considered available. Defaults to 0 (pod
 *   will be considered available as soon as it is ready)
 * @param paused Indicates that the deployment is paused.
 * @param progressDeadlineSeconds The maximum time in seconds for a deployment to make progress
 *   before it is considered to be failed. The deployment controller will continue to process failed
 *   deployments and a condition with a ProgressDeadlineExceeded reason will be surfaced in the
 *   deployment status. Note that progress will not be estimated during the time a deployment is
 *   paused. Defaults to 600s.
 * @param replicas Number of desired pods. This is a pointer to distinguish between explicit zero
 *   and not specified. Defaults to 1.
 * @param revisionHistoryLimit The number of old ReplicaSets to retain to allow rollback. This is a
 *   pointer to distinguish between explicit zero and not specified. Defaults to 10.
 * @param selector Label selector for pods. Existing ReplicaSets whose pods are selected by this
 *   will be the ones affected by this deployment. It must match the pod template's labels.
 * @param strategy The deployment strategy to use to replace existing pods with new ones.
 * @param template Template describes the pods that will be created. The only allowed
 *   template.spec.restartPolicy value is "Always".
 */
@Serializable
public data class DeploymentSpec(
    public val minReadySeconds: Int,
    public val paused: Boolean,
    public val progressDeadlineSeconds: Int,
    public val replicas: Int,
    public val revisionHistoryLimit: Int,
    public val selector: LabelSelector,
    public val strategy: DeploymentStrategy,
    public val template: PodTemplateSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "DeploymentSpec"
}
