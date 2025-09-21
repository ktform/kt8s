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

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param endPort endPort indicates that the range of ports from port to endPort if set, inclusive,
 *   should be allowed by the policy. This field cannot be defined if the port field is not defined
 *   or if the port field is defined as a named (string) port. The endPort must be equal or greater
 *   than port.
 * @param port port represents the port on the given protocol. This can either be a numerical or
 *   named port on a pod. If this field is not provided, this matches all port names and numbers. If
 *   present, only traffic on the specified protocol AND port will be matched.
 * @param protocol protocol represents the protocol (TCP, UDP, or SCTP) which traffic must match. If
 *   not specified, this field defaults to TCP.
 */
@Serializable
public data class NetworkPolicyPort(
    public val endPort: Int,
    public val port: IntOrString,
    public val protocol: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NetworkPolicyPort"
}
