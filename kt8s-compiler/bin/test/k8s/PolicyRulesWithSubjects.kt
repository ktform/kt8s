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
 * @param nonResourceRules `nonResourceRules` is a list of NonResourcePolicyRules that identify
 *   matching requests according to their verb and the target non-resource URL.
 * @param resourceRules `resourceRules` is a slice of ResourcePolicyRules that identify matching
 *   requests according to their verb and the target resource. At least one of `resourceRules` and
 *   `nonResourceRules` has to be non-empty.
 * @param subjects subjects is the list of normal user, serviceaccount, or group that this rule
 *   cares about. There must be at least one member in this slice. A slice that includes both the
 *   system:authenticated and system:unauthenticated user groups matches every request. Required.
 */
@Serializable
public data class PolicyRulesWithSubjects(
    public val nonResourceRules: List<NonResourcePolicyRule>,
    public val resourceRules: List<ResourcePolicyRule>,
    public val subjects: List<Subject>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

    @Transient override val group: String = "io.k8s.api.flowcontrol"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PolicyRulesWithSubjects"
}
