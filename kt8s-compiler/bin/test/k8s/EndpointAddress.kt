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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param hostname The Hostname of this endpoint
 * @param ip The IP of this endpoint. May not be loopback (127.0.0.0/8 or ::1), link-local
 *   (169.254.0.0/16 or fe80::/10), or link-local multicast (224.0.0.0/24 or ff02::/16).
 * @param nodeName Optional: Node hosting this endpoint. This can be used to determine endpoints
 *   local to a node.
 * @param targetRef Reference to object providing the endpoint.
 */
@Serializable
public data class EndpointAddress(
    public val hostname: String,
    public val ip: String,
    public val nodeName: String,
    public val targetRef: ObjectReference,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "EndpointAddress"
}
