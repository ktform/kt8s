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
 * @param apiGroups APIGroups is the name of the APIGroup that contains the resources. If multiple
 *   API groups are specified, any action requested against one of the enumerated resources in any
 *   API group will be allowed. "" represents the core API group and "*" represents all API groups.
 * @param nonResourceURLs NonResourceURLs is a set of partial urls that a user should have access
 *   to. *s are allowed, but only as the full, final step in the path Since non-resource URLs are
 *   not namespaced, this field is only applicable for ClusterRoles referenced from a
 *   ClusterRoleBinding. Rules can either apply to API resources (such as "pods" or "secrets") or
 *   non-resource URL paths (such as "/api"), but not both.
 * @param resourceNames ResourceNames is an optional white list of names that the rule applies to.
 *   An empty set means that everything is allowed.
 * @param resources Resources is a list of resources this rule applies to. '*' represents all
 *   resources.
 * @param verbs Verbs is a list of Verbs that apply to ALL the ResourceKinds contained in this rule.
 *   '*' represents all verbs.
 */
@Serializable
public data class PolicyRule(
    public val apiGroups: List<String>,
    public val nonResourceURLs: List<String>,
    public val resourceNames: List<String>,
    public val resources: List<String>,
    public val verbs: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.rbac/v1"

    @Transient override val group: String = "io.k8s.api.rbac"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PolicyRule"
}
