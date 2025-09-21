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
 * This field can only be set when deviceClassName is set and no subrequests are specified in the
 * firstAvailable list.
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
 * This field can only be set when deviceClassName is set and no subrequests are specified in the
 * firstAvailable list.
 *
 * More modes may get added in the future. Clients must refuse to handle requests with unknown
 * modes.
 *
 * @param count Count is used only when the count mode is "ExactCount". Must be greater than zero.
 *   If AllocationMode is ExactCount and this field is not specified, the default is one.
 *
 * This field can only be set when deviceClassName is set and no subrequests are specified in the
 * firstAvailable list.
 *
 * @param deviceClassName DeviceClassName references a specific DeviceClass, which can define
 *   additional configuration and selectors to be inherited by this request.
 *
 * A class is required if no subrequests are specified in the firstAvailable list and no class can
 * be set if subrequests are specified in the firstAvailable list. Which classes are available
 * depends on the cluster.
 *
 * Administrators may use this to restrict which devices may get requested by only installing
 * classes with selectors for permitted devices. If users are free to request anything without
 * restrictions, then administrators can create an empty DeviceClass for users to reference.
 *
 * @param firstAvailable FirstAvailable contains subrequests, of which exactly one will be satisfied
 *   by the scheduler to satisfy this request. It tries to satisfy them in the order in which they
 *   are listed here. So if there are two entries in the list, the scheduler will only check the
 *   second one if it determines that the first one cannot be used.
 *
 * This field may only be set in the entries of DeviceClaim.Requests.
 *
 * DRA does not yet implement scoring, so the scheduler will select the first set of devices that
 * satisfies all the requests in the claim. And if the requirements can be satisfied on more than
 * one node, other scheduling features will determine which node is chosen. This means that the set
 * of devices allocated to a claim might not be the optimal set available to the cluster. Scoring
 * will be implemented later.
 *
 * @param name Name can be used to reference this request in a
 *   pod.spec.containers[].resources.claims entry and in a constraint of the claim.
 *
 * Must be a DNS label.
 *
 * @param selectors Selectors define criteria which must be satisfied by a specific device in order
 *   for that device to be considered for this request. All selectors must be satisfied for a device
 *   to be considered.
 *
 * This field can only be set when deviceClassName is set and no subrequests are specified in the
 * firstAvailable list.
 *
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
 * This field can only be set when deviceClassName is set and no subrequests are specified in the
 * firstAvailable list.
 *
 * This is an alpha field and requires enabling the DRADeviceTaints feature gate.
 */
@Serializable
public data class DeviceRequest(
    public val adminAccess: Boolean,
    public val allocationMode: String,
    public val count: Long,
    public val deviceClassName: String,
    public val firstAvailable: List<DeviceSubRequest>,
    public val name: String,
    public val selectors: List<DeviceSelector>,
    public val tolerations: List<DeviceToleration>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceRequest"
}
