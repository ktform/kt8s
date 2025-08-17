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
 * @param opaque Opaque provides driver-specific configuration parameters.
 * @param requests Requests lists the names of requests where the configuration applies. If empty,
 *   its applies to all requests.
 *
 * References to subrequests must include the name of the main request and may include the
 * subrequest using the format <main request>[/<subrequest>]. If just the main request is given, the
 * configuration applies to all subrequests.
 *
 * @param source Source records whether the configuration comes from a class and thus is not
 *   something that a normal user would have been able to set or from a claim.
 */
@Serializable
public data class DeviceAllocationConfiguration(
    public val opaque: OpaqueDeviceConfiguration,
    public val requests: List<String>,
    public val source: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceAllocationConfiguration"
}
