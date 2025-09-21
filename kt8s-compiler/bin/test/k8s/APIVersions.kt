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
 * @param serverAddressByClientCIDRs a map of client CIDR to server address that is serving this
 *   group. This is to help clients reach servers in the most network-efficient way possible.
 *   Clients can use the appropriate server address as per the CIDR that they match. In case of
 *   multiple matches, clients should use the longest matching CIDR. The server returns only those
 *   CIDRs that it thinks that the client can match. For example: the master will return an internal
 *   IP CIDR only, if the client reaches the server using an internal IP. Server looks at
 *   X-Forwarded-For header or X-Real-Ip header or request.RemoteAddr (in that order) to get the
 *   client IP.
 * @param versions versions are the api versions that are available.
 */
@Serializable
public data class APIVersions(
    public val serverAddressByClientCIDRs: List<ServerAddressByClientCIDR>,
    public val versions: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "v1"

    @Transient override val group: String = ""

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "APIVersions"
}
