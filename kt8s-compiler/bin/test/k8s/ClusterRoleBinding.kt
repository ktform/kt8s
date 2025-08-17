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
 * @param metadata Standard object's metadata.
 * @param roleRef RoleRef can only reference a ClusterRole in the global namespace. If the RoleRef
 *   cannot be resolved, the Authorizer must return an error. This field is immutable.
 * @param subjects Subjects holds references to the objects the role applies to.
 */
@Serializable
public data class ClusterRoleBinding(
    public val metadata: ObjectMeta,
    public val roleRef: RoleRef,
    public val subjects: List<Subject>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "rbac.authorization.k8s.io/v1"

    @Transient override val group: String = "rbac.authorization.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ClusterRoleBinding"
}
