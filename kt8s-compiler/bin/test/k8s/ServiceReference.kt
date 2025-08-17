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
 * @param name `name` is the name of the service. Required
 * @param namespace `namespace` is the namespace of the service. Required
 * @param path `path` is an optional URL path which will be sent in any request to this service.
 * @param port If specified, the port on the service that hosting webhook. Default to 443 for
 *   backward compatibility. `port` should be a valid port number (1-65535, inclusive).
 */
@Serializable
public data class ServiceReference(
    public val name: String,
    public val namespace: String,
    public val path: String,
    public val port: Int,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ServiceReference"
}
