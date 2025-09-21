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
 * @param clientCIDR The CIDR with which clients can match their IP to figure out the server address
 *   that they should use.
 * @param serverAddress Address of this server, suitable for a client that matches the above CIDR.
 *   This can be a hostname, hostname:port, IP or IP:port.
 */
@Serializable
public data class ServerAddressByClientCIDR(
    public val clientCIDR: String,
    public val serverAddress: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

    @Transient override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ServerAddressByClientCIDR"
}
