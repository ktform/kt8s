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
 * @param name Name uniquely identifies this resource claim inside the pod. This must match the name
 *   of an entry in pod.spec.resourceClaims, which implies that the string must be a DNS_LABEL.
 * @param resourceClaimName ResourceClaimName is the name of the ResourceClaim that was generated
 *   for the Pod in the namespace of the Pod. If this is unset, then generating a ResourceClaim was
 *   not necessary. The pod.spec.resourceClaims entry can be ignored in this case.
 */
@Serializable
public data class PodResourceClaimStatus(
    public val name: String,
    public val resourceClaimName: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodResourceClaimStatus"
}
