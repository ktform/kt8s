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
 * @param rules rules represents the list of alternative rules for the declaring the Jobs as
 *   successful before `.status.succeeded >= .spec.completions`. Once any of the rules are met, the
 *   "SucceededCriteriaMet" condition is added, and the lingering pods are removed. The terminal
 *   state for such a Job has the "Complete" condition. Additionally, these rules are evaluated in
 *   order; Once the Job meets one of the rules, other rules are ignored. At most 20 elements are
 *   allowed.
 */
@Serializable
public data class SuccessPolicy(public val rules: List<SuccessPolicyRule>) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.batch/v1"

    @Transient override val group: String = "io.k8s.api.batch"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SuccessPolicy"
}
