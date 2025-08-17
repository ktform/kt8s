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
 * @param fieldSelector fieldSelector describes the limitation on access based on field. It can only
 *   limit access, not broaden it.
 *
 * This field is alpha-level. To use this field, you must enable the `AuthorizeWithSelectors`
 * feature gate (disabled by default).
 *
 * @param labelSelector labelSelector describes the limitation on access based on labels. It can
 *   only limit access, not broaden it.
 *
 * This field is alpha-level. To use this field, you must enable the `AuthorizeWithSelectors`
 * feature gate (disabled by default).
 *
 * @param name Name is the name of the resource being requested for a "get" or deleted for a
 *   "delete". "" (empty) means all.
 * @param namespace Namespace is the namespace of the action being requested. Currently, there is no
 *   distinction between no namespace and all namespaces "" (empty) is defaulted for
 *   LocalSubjectAccessReviews "" (empty) is empty for cluster-scoped resources "" (empty) means
 *   "all" for namespace scoped resources from a SubjectAccessReview or SelfSubjectAccessReview
 * @param resource Resource is one of the existing resource types. "*" means all.
 * @param subresource Subresource is one of the existing resource types. "" means none.
 * @param verb Verb is a kubernetes resource API verb, like: get, list, watch, create, update,
 *   delete, proxy. "*" means all.
 */
@Serializable
public data class ResourceAttributes(
    public val fieldSelector: FieldSelectorAttributes,
    public val labelSelector: LabelSelectorAttributes,
    public val name: String,
    public val namespace: String,
    public val resource: String,
    public val subresource: String,
    public val verb: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.authorization/v1"

    @Transient override val group: String = "io.k8s.api.authorization"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ResourceAttributes"
}
