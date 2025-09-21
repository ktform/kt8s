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
 * @param matchAttribute MatchAttribute requires that all devices in question have this attribute
 *   and that its type and value are the same across those devices.
 *
 * For example, if you specified "dra.example.com/numa" (a hypothetical example!), then only devices
 * in the same NUMA node will be chosen. A device which does not have that attribute will not be
 * chosen. All devices should use a value of the same type for this attribute because that is part
 * of its specification, but if one device doesn't, then it also will not be chosen.
 *
 * Must include the domain qualifier.
 *
 * @param requests Requests is a list of the one or more requests in this claim which must
 *   co-satisfy this constraint. If a request is fulfilled by multiple devices, then all of the
 *   devices must satisfy the constraint. If this is not specified, this constraint applies to all
 *   requests in this claim.
 *
 * References to subrequests must include the name of the main request and may include the
 * subrequest using the format <main request>[/<subrequest>]. If just the main request is given, the
 * constraint applies to all subrequests.
 */
@Serializable
public data class DeviceConstraint(
    public val matchAttribute: String,
    public val requests: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceConstraint"
}
