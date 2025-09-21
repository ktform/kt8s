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
 * @param adminAccess AdminAccess indicates that this device was allocated for administrative
 *   access. See the corresponding request field for a definition of mode.
 *
 * This is an alpha field and requires enabling the DRAAdminAccess feature gate. Admin access is
 * disabled if this field is unset or set to false, otherwise it is enabled.
 *
 * @param device Device references one device instance via its name in the driver's resource pool.
 *   It must be a DNS label.
 * @param driver Driver specifies the name of the DRA driver whose kubelet plugin should be invoked
 *   to process the allocation once the claim is needed on a node.
 *
 * Must be a DNS subdomain and should end with a DNS domain owned by the vendor of the driver.
 *
 * @param pool This name together with the driver name and the device name field identify which
 *   device was allocated (`<driver name>/<pool name>/<device name>`).
 *
 * Must not be longer than 253 characters and may contain one or more DNS sub-domains separated by
 * slashes.
 *
 * @param request Request is the name of the request in the claim which caused this device to be
 *   allocated. If it references a subrequest in the firstAvailable list on a DeviceRequest, this
 *   field must include both the name of the main request and the subrequest using the format <main
 *   request>/<subrequest>.
 *
 * Multiple devices may have been allocated per request.
 *
 * @param tolerations A copy of all tolerations specified in the request at the time when the device
 *   got allocated.
 *
 * The maximum number of tolerations is 16.
 *
 * This is an alpha field and requires enabling the DRADeviceTaints feature gate.
 */
@Serializable
public data class DeviceRequestAllocationResult(
    public val adminAccess: Boolean,
    public val device: String,
    public val driver: String,
    public val pool: String,
    public val request: String,
    public val tolerations: List<DeviceToleration>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceRequestAllocationResult"
}
