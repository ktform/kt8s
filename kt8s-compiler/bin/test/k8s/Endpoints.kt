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
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param subsets The set of all endpoints is the union of all subsets. Addresses are placed into
 *   subsets according to the IPs they share. A single address with multiple ports, some of which
 *   are ready and some of which are not (because they come from different containers) will result
 *   in the address being displayed in different subsets for the different ports. No address will
 *   appear in both Addresses and NotReadyAddresses in the same subset. Sets of addresses and ports
 *   that comprise a service.
 */
@Serializable
public data class Endpoints(
    public val metadata: ObjectMeta,
    public val subsets: List<EndpointSubset>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "v1"

    @Transient override val group: String = ""

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Endpoints"
}
