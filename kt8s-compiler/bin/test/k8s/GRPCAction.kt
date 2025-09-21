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
 * @param port Port number of the gRPC service. Number must be in the range 1 to 65535.
 * @param service Service is the name of the service to place in the gRPC HealthCheckRequest (see
 *   https://github.com/grpc/grpc/blob/master/doc/health-checking.md).
 *
 * If this is not specified, the default behavior is defined by gRPC.
 */
@Serializable
public data class GRPCAction(public val port: Int, public val service: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "GRPCAction"
}
