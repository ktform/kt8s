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
 * @param allocation Allocation is set once the claim has been allocated successfully.
 * @param devices Devices contains the status of each device allocated for this claim, as reported
 *   by the driver. This can include driver-specific information. Entries are owned by their
 *   respective drivers.
 * @param reservedFor ReservedFor indicates which entities are currently allowed to use the claim. A
 *   Pod which references a ResourceClaim which is not reserved for that Pod will not be started. A
 *   claim that is in use or might be in use because it has been reserved must not get deallocated.
 *
 * In a cluster with multiple scheduler instances, two pods might get scheduled concurrently by
 * different schedulers. When they reference the same ResourceClaim which already has reached its
 * maximum number of consumers, only one pod can be scheduled.
 *
 * Both schedulers try to add their pod to the claim.status.reservedFor field, but only the update
 * that reaches the API server first gets stored. The other one fails with an error and the
 * scheduler which issued it knows that it must put the pod back into the queue, waiting for the
 * ResourceClaim to become usable again.
 *
 * There can be at most 256 such reservations. This may get increased in the future, but not
 * reduced.
 */
@Serializable
public data class ResourceClaimStatus(
    public val allocation: AllocationResult,
    public val devices: List<AllocatedDeviceStatus>,
    public val reservedFor: List<ResourceClaimConsumerReference>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "ResourceClaimStatus"
}
