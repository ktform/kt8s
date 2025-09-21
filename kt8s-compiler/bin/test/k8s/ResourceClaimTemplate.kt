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
 * @param metadata Standard object metadata
 * @param spec Describes the ResourceClaim that is to be generated.
 *
 * This field is immutable. A ResourceClaim will get created by the control plane for a Pod when
 * needed and then not get updated anymore.
 */
@Serializable
public data class ResourceClaimTemplate(
    public val metadata: ObjectMeta,
    public val spec: ResourceClaimTemplateSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "resource.k8s.io/v1alpha3"

    @Transient override val group: String = "resource.k8s.io"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "ResourceClaimTemplate"
}
