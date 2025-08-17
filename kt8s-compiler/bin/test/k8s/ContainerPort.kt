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
 * @param containerPort Number of port to expose on the pod's IP address. This must be a valid port
 *   number, 0 < x < 65536.
 * @param hostIP What host IP to bind the external port to.
 * @param hostPort Number of port to expose on the host. If specified, this must be a valid port
 *   number, 0 < x < 65536. If HostNetwork is specified, this must match ContainerPort. Most
 *   containers do not need this.
 * @param name If specified, this must be an IANA_SVC_NAME and unique within the pod. Each named
 *   port in a pod must have a unique name. Name for the port that can be referred to by services.
 * @param protocol Protocol for port. Must be UDP, TCP, or SCTP. Defaults to "TCP".
 */
@Serializable
public data class ContainerPort(
    public val containerPort: Int,
    public val hostIP: String,
    public val hostPort: Int,
    public val name: String,
    public val protocol: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ContainerPort"
}
