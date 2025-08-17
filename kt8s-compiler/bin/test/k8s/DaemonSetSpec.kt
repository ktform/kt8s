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
 * @param minReadySeconds The minimum number of seconds for which a newly created DaemonSet pod
 *   should be ready without any of its container crashing, for it to be considered available.
 *   Defaults to 0 (pod will be considered available as soon as it is ready).
 * @param revisionHistoryLimit The number of old history to retain to allow rollback. This is a
 *   pointer to distinguish between explicit zero and not specified. Defaults to 10.
 * @param selector A label query over pods that are managed by the daemon set. Must match in order
 *   to be controlled. It must match the pod template's labels. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/#label-selectors
 * @param template An object that describes the pod that will be created. The DaemonSet will create
 *   exactly one copy of this pod on every node that matches the template's node selector (or on
 *   every node if no node selector is specified). The only allowed template.spec.restartPolicy
 *   value is "Always". More info:
 *   https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller#pod-template
 * @param updateStrategy An update strategy to replace existing DaemonSet pods with new pods.
 */
@Serializable
public data class DaemonSetSpec(
    public val minReadySeconds: Int,
    public val revisionHistoryLimit: Int,
    public val selector: LabelSelector,
    public val template: PodTemplateSpec,
    public val updateStrategy: DaemonSetUpdateStrategy,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "DaemonSetSpec"
}
