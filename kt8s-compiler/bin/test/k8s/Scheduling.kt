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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param nodeSelector nodeSelector lists labels that must be present on nodes that support this
 *   RuntimeClass. Pods using this RuntimeClass can only be scheduled to a node matched by this
 *   selector. The RuntimeClass nodeSelector is merged with a pod's existing nodeSelector. Any
 *   conflicts will cause the pod to be rejected in admission.
 * @param tolerations tolerations are appended (excluding duplicates) to pods running with this
 *   RuntimeClass during admission, effectively unioning the set of nodes tolerated by the pod and
 *   the RuntimeClass.
 */
@Serializable
public data class Scheduling(
    public val nodeSelector: RawJsonObject,
    public val tolerations: List<Toleration>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.node/v1"

    @Transient override val group: String = "io.k8s.api.node"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Scheduling"
}
