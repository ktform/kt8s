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
 * @param config This field is a combination of all the claim and class configuration parameters.
 *   Drivers can distinguish between those based on a flag.
 *
 * This includes configuration parameters for drivers which have no allocated devices in the result
 * because it is up to the drivers which configuration parameters they support. They can silently
 * ignore unknown configuration parameters.
 *
 * @param results Results lists all allocated devices.
 */
@Serializable
public data class DeviceAllocationResult(
    public val config: List<DeviceAllocationConfiguration>,
    public val results: List<DeviceRequestAllocationResult>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceAllocationResult"
}
