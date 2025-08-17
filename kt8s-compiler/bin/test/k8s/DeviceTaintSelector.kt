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
 * @param device If device is set, only devices with that name are selected. This field corresponds
 *   to slice.spec.devices[].name.
 *
 * Setting also driver and pool may be required to avoid ambiguity, but is not required.
 *
 * @param deviceClassName If DeviceClassName is set, the selectors defined there must be satisfied
 *   by a device to be selected. This field corresponds to class.metadata.name.
 * @param driver If driver is set, only devices from that driver are selected. This fields
 *   corresponds to slice.spec.driver.
 * @param pool If pool is set, only devices in that pool are selected.
 *
 * Also setting the driver name may be useful to avoid ambiguity when different drivers use the same
 * pool name, but this is not required because selecting pools from different drivers may also be
 * useful, for example when drivers with node-local devices use the node name as their pool name.
 *
 * @param selectors Selectors contains the same selection criteria as a ResourceClaim. Currently,
 *   CEL expressions are supported. All of these selectors must be satisfied.
 */
@Serializable
public data class DeviceTaintSelector(
    public val device: String,
    public val deviceClassName: String,
    public val driver: String,
    public val pool: String,
    public val selectors: List<DeviceSelector>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceTaintSelector"
}
