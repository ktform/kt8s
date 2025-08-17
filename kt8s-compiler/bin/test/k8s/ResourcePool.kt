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

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param generation Generation tracks the change in a pool over time. Whenever a driver changes
 *   something about one or more of the resources in a pool, it must change the generation in all
 *   ResourceSlices which are part of that pool. Consumers of ResourceSlices should only consider
 *   resources from the pool with the highest generation number. The generation may be reset by
 *   drivers, which should be fine for consumers, assuming that all ResourceSlices in a pool are
 *   updated to match or deleted.
 *
 * Combined with ResourceSliceCount, this mechanism enables consumers to detect pools which are
 * comprised of multiple ResourceSlices and are in an incomplete state.
 *
 * @param name Name is used to identify the pool. For node-local devices, this is often the node
 *   name, but this is not required.
 *
 * It must not be longer than 253 characters and must consist of one or more DNS sub-domains
 * separated by slashes. This field is immutable.
 *
 * @param resourceSliceCount ResourceSliceCount is the total number of ResourceSlices in the pool at
 *   this generation number. Must be greater than zero.
 *
 * Consumers can use this to check whether they have seen all ResourceSlices belonging to the same
 * pool.
 */
@Serializable
public data class ResourcePool(
    public val generation: Long,
    public val name: String,
    public val resourceSliceCount: Long,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "ResourcePool"
}
