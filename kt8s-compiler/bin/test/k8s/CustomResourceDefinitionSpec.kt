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

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param conversion conversion defines conversion settings for the CRD.
 * @param names names specify the resource and kind names for the custom resource.
 * @param preserveUnknownFields preserveUnknownFields indicates that object fields which are not
 *   specified in the OpenAPI schema should be preserved when persisting to storage. apiVersion,
 *   kind, metadata and known fields inside metadata are always preserved. This field is deprecated
 *   in favor of setting `x-preserve-unknown-fields` to true in
 *   `spec.versions[*].schema.openAPIV3Schema`. See
 *   https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/#field-pruning
 *   for details.
 * @param scope scope indicates whether the defined custom resource is cluster- or namespace-scoped.
 *   Allowed values are `Cluster` and `Namespaced`.
 * @param versions versions is the list of all API versions of the defined custom resource. Version
 *   names are used to compute the order in which served versions are listed in API discovery. If
 *   the version string is "kube-like", it will sort above non "kube-like" version strings, which
 *   are ordered lexicographically. "Kube-like" versions start with a "v", then are followed by a
 *   number (the major version), then optionally the string "alpha" or "beta" and another number
 *   (the minor version). These are sorted first by GA > beta > alpha (where GA is a version with no
 *   suffix such as beta or alpha), and then by comparing major version, then minor version. An
 *   example sorted list of versions: v10, v2, v1, v11beta2, v10beta3, v3beta1, v12alpha1,
 *   v11alpha2, foo1, foo10.
 */
@Serializable
public data class CustomResourceDefinitionSpec(
    public val conversion: CustomResourceConversion,
    public val names: CustomResourceDefinitionNames,
    public val preserveUnknownFields: Boolean,
    public val scope: String,
    public val versions: List<CustomResourceDefinitionVersion>,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

    @Transient override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CustomResourceDefinitionSpec"
}
