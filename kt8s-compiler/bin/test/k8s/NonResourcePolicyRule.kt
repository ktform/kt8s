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
 * @param nonResourceURLs `nonResourceURLs` is a set of url prefixes that a user should have access
 *   to and may not be empty. For example:
 *     - "/healthz" is legal
 *     - "/hea*" is illegal
 *     - "/hea" is legal but matches nothing
 *     - "/hea/\*" also matches nothing
 *     - "/healthz/\*" matches all per-component health checks. "*" matches all non-resource urls.
 *       if it is present, it must be the only entry. Required.
 *
 * @param verbs `verbs` is a list of matching verbs and may not be empty. "*" matches all verbs. If
 *   it is present, it must be the only entry. Required.
 */
@Serializable
public data class NonResourcePolicyRule(
    public val nonResourceURLs: List<String>,
    public val verbs: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

    @Transient override val group: String = "io.k8s.api.flowcontrol"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NonResourcePolicyRule"
}
