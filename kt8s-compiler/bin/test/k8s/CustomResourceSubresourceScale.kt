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
 * @param labelSelectorPath labelSelectorPath defines the JSON path inside of a custom resource that
 *   corresponds to Scale `status.selector`. Only JSON paths without the array notation are allowed.
 *   Must be a JSON Path under `.status` or `.spec`. Must be set to work with
 *   HorizontalPodAutoscaler. The field pointed by this JSON path must be a string field (not a
 *   complex selector struct) which contains a serialized label selector in string form. More info:
 *   https://kubernetes.io/docs/tasks/access-kubernetes-api/custom-resources/custom-resource-definitions#scale-subresource
 *   If there is no value under the given path in the custom resource, the `status.selector` value
 *   in the `/scale` subresource will default to the empty string.
 * @param specReplicasPath specReplicasPath defines the JSON path inside of a custom resource that
 *   corresponds to Scale `spec.replicas`. Only JSON paths without the array notation are allowed.
 *   Must be a JSON Path under `.spec`. If there is no value under the given path in the custom
 *   resource, the `/scale` subresource will return an error on GET.
 * @param statusReplicasPath statusReplicasPath defines the JSON path inside of a custom resource
 *   that corresponds to Scale `status.replicas`. Only JSON paths without the array notation are
 *   allowed. Must be a JSON Path under `.status`. If there is no value under the given path in the
 *   custom resource, the `status.replicas` value in the `/scale` subresource will default to 0.
 */
@Serializable
public data class CustomResourceSubresourceScale(
    public val labelSelectorPath: String,
    public val specReplicasPath: String,
    public val statusReplicasPath: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

    @Transient override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CustomResourceSubresourceScale"
}
