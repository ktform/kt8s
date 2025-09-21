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
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param adminAccess AdminAccess indicates that this is a claim for administrative access to the
 *   device(s). Claims with AdminAccess are expected to be used for monitoring or other management
 *   services for a device. They ignore all ordinary claims to the device with respect to access
 *   modes and any resource allocations.
 *
 * This is an alpha field and requires enabling the DRAAdminAccess feature gate. Admin access is
 * disabled if this field is unset or set to false, otherwise it is enabled.
 *
 * @param allocationMode AllocationMode and its related fields define how devices are allocated to
 *   satisfy this request. Supported values are:
 * - ExactCount: This request is for a specific number of devices. This is the default. The exact
 *   number is provided in the count field.
 * - All: This request is for all of the matching devices in a pool. At least one device must exist
 *   on the node for the allocation to succeed. Allocation will fail if some devices are already
 *   allocated, unless adminAccess is requested.
 *
 * If AllocationMode is not specified, the default mode is ExactCount. If the mode is ExactCount and
 * count is not specified, the default count is one. Any other requests must specify this field.
 *
 * More modes may get added in the future. Clients must refuse to handle requests with unknown
 * modes.
 *
 * @param count Count is used only when the count mode is "ExactCount". Must be greater than zero.
 *   If AllocationMode is ExactCount and this field is not specified, the default is one.
 * @param deviceClassName DeviceClassName references a specific DeviceClass, which can define
 *   additional configuration and selectors to be inherited by this request.
 *
 * A DeviceClassName is required.
 *
 * Administrators may use this to restrict which devices may get requested by only installing
 * classes with selectors for permitted devices. If users are free to request anything without
 * restrictions, then administrators can create an empty DeviceClass for users to reference.
 *
 * @param selectors Selectors define criteria which must be satisfied by a specific device in order
 *   for that device to be considered for this request. All selectors must be satisfied for a device
 *   to be considered.
 * @param tolerations If specified, the request's tolerations.
 *
 * Tolerations for NoSchedule are required to allocate a device which has a taint with that effect.
 * The same applies to NoExecute.
 *
 * In addition, should any of the allocated devices get tainted with NoExecute after allocation and
 * that effect is not tolerated, then all pods consuming the ResourceClaim get deleted to evict
 * them. The scheduler will not let new pods reserve the claim while it has these tainted devices.
 * Once all pods are evicted, the claim will get deallocated.
 *
 * The maximum number of tolerations is 16.
 *
 * This is an alpha field and requires enabling the DRADeviceTaints feature gate.
 */
@Serializable
public data class ExactDeviceRequest(
    public val adminAccess: Boolean,
    public val allocationMode: String,
    public val count: Long,
    public val deviceClassName: String,
    public val selectors: List<DeviceSelector>,
    public val tolerations: List<DeviceToleration>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1beta2"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1beta2"

    @SerialName("kind") override val kind: String = "ExactDeviceRequest"
}
