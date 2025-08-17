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
 * @param effect Required. The effect of the taint on pods that do not tolerate the taint. Valid
 *   effects are NoSchedule, PreferNoSchedule and NoExecute.
 * @param key Required. The taint key to be applied to a node.
 * @param timeAdded TimeAdded represents the time at which the taint was added. It is only written
 *   for NoExecute taints.
 * @param value The taint value corresponding to the taint key.
 */
@Serializable
public data class Taint(
    public val effect: String,
    public val key: String,
    public val timeAdded: KubernetesTime,
    public val `value`: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Taint"
}
