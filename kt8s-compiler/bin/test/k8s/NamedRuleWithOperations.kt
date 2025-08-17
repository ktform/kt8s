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
 * @param apiGroups APIGroups is the API groups the resources belong to. '*' is all groups. If '*'
 *   is present, the length of the slice must be one. Required.
 * @param apiVersions APIVersions is the API versions the resources belong to. '*' is all versions.
 *   If '*' is present, the length of the slice must be one. Required.
 * @param operations Operations is the operations the admission hook cares about - CREATE, UPDATE,
 *   DELETE, CONNECT or * for all of those operations and any future admission operations that are
 *   added. If '*' is present, the length of the slice must be one. Required.
 * @param resourceNames ResourceNames is an optional white list of names that the rule applies to.
 *   An empty set means that everything is allowed.
 * @param resources Resources is a list of resources this rule applies to.
 *
 * For example: 'pods' means pods. 'pods/log' means the log subresource of pods. '*' means all
 * resources, but not subresources. 'pods/\*' means all subresources of pods. '*\/scale' means all
 * scale subresources. '*\/\*' means all resources and their subresources.
 *
 * If wildcard is present, the validation rule will ensure resources do not overlap with each other.
 *
 * Depending on the enclosing object, subresources might not be allowed. Required.
 *
 * @param scope scope specifies the scope of this rule. Valid values are "Cluster", "Namespaced",
 *   and "*" "Cluster" means that only cluster-scoped resources will match this rule. Namespace API
 *   objects are cluster-scoped. "Namespaced" means that only namespaced resources will match this
 *   rule. "*" means that there are no scope restrictions. Subresources match the scope of their
 *   parent resource. Default is "*".
 */
@Serializable
public data class NamedRuleWithOperations(
    public val apiGroups: List<String>,
    public val apiVersions: List<String>,
    public val operations: List<String>,
    public val resourceNames: List<String>,
    public val resources: List<String>,
    public val scope: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NamedRuleWithOperations"
}
