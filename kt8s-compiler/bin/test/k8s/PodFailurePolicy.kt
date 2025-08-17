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
 * @param rules A list of pod failure policy rules. The rules are evaluated in order. Once a rule
 *   matches a Pod failure, the remaining of the rules are ignored. When no rule matches the Pod
 *   failure, the default handling applies - the counter of pod failures is incremented and it is
 *   checked against the backoffLimit. At most 20 elements are allowed.
 */
@Serializable
public data class PodFailurePolicy(public val rules: List<PodFailurePolicyRule>) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.batch/v1"

    @Transient override val group: String = "io.k8s.api.batch"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodFailurePolicy"
}
