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

import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param conditions The conditions represent the latest available observations of a policy's
 *   current state.
 * @param observedGeneration The generation observed by the controller.
 * @param typeChecking The results of type checking for each expression. Presence of this field
 *   indicates the completion of the type checking.
 */
@Serializable
public data class ValidatingAdmissionPolicyStatus(
    public val conditions: List<Condition>,
    public val observedGeneration: Long,
    public val typeChecking: TypeChecking,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ValidatingAdmissionPolicyStatus"
}
