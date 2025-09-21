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
 * @param name Name uniquely identifies this resource claim inside the pod. This must be a
 *   DNS_LABEL.
 * @param resourceClaimName ResourceClaimName is the name of a ResourceClaim object in the same
 *   namespace as this pod.
 *
 * Exactly one of ResourceClaimName and ResourceClaimTemplateName must be set.
 *
 * @param resourceClaimTemplateName ResourceClaimTemplateName is the name of a ResourceClaimTemplate
 *   object in the same namespace as this pod.
 *
 * The template will be used to create a new ResourceClaim, which will be bound to this pod. When
 * this pod is deleted, the ResourceClaim will also be deleted. The pod name and resource name,
 * along with a generated component, will be used to form a unique name for the ResourceClaim, which
 * will be recorded in pod.status.resourceClaimStatuses.
 *
 * This field is immutable and no changes will be made to the corresponding ResourceClaim by the
 * control plane after creating the ResourceClaim.
 *
 * Exactly one of ResourceClaimName and ResourceClaimTemplateName must be set.
 */
@Serializable
public data class PodResourceClaim(
    public val name: String,
    public val resourceClaimName: String,
    public val resourceClaimTemplateName: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodResourceClaim"
}
