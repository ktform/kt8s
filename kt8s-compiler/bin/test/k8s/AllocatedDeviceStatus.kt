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
 * @param conditions Conditions contains the latest observation of the device's state. If the device
 *   has been configured according to the class and claim config references, the `Ready` condition
 *   should be True.
 *
 * Must not contain more than 8 entries.
 *
 * @param data Data contains arbitrary driver-specific data.
 *
 * The length of the raw data must be smaller or equal to 10 Ki.
 *
 * @param device Device references one device instance via its name in the driver's resource pool.
 *   It must be a DNS label.
 * @param driver Driver specifies the name of the DRA driver whose kubelet plugin should be invoked
 *   to process the allocation once the claim is needed on a node.
 *
 * Must be a DNS subdomain and should end with a DNS domain owned by the vendor of the driver.
 *
 * @param networkData NetworkData contains network-related information specific to the device.
 * @param pool This name together with the driver name and the device name field identify which
 *   device was allocated (`<driver name>/<pool name>/<device name>`).
 *
 * Must not be longer than 253 characters and may contain one or more DNS sub-domains separated by
 * slashes.
 */
@Serializable
public data class AllocatedDeviceStatus(
    public val conditions: List<Condition>,
    public val `data`: RawJsonObject,
    public val device: String,
    public val driver: String,
    public val networkData: NetworkDeviceData,
    public val pool: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "AllocatedDeviceStatus"
}
