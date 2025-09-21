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
 * @param error Error is to record the problem with the service port The format of the error shall
 *   comply with the following rules: - built-in error values shall be specified in this file and
 *   those shall use CamelCase names
 * - cloud provider specific error values must have names that comply with the format
 *   foo.example.com/CamelCase.
 *
 * @param port Port is the port number of the service port of which status is recorded here
 * @param protocol Protocol is the protocol of the service port of which status is recorded here The
 *   supported values are: "TCP", "UDP", "SCTP"
 */
@Serializable
public data class PortStatus(
    public val error: String,
    public val port: Int,
    public val protocol: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PortStatus"
}
