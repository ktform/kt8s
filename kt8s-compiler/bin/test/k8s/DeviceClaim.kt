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
 * @param config This field holds configuration for multiple potential drivers which could satisfy
 *   requests in this claim. It is ignored while allocating the claim.
 * @param constraints These constraints must be satisfied by the set of devices that get allocated
 *   for the claim.
 * @param requests Requests represent individual requests for distinct devices which must all be
 *   satisfied. If empty, nothing needs to be allocated.
 */
@Serializable
public data class DeviceClaim(
    public val config: List<DeviceClaimConfiguration>,
    public val constraints: List<DeviceConstraint>,
    public val requests: List<DeviceRequest>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceClaim"
}
