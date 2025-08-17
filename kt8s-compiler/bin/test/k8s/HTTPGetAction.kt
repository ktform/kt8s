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
 * @param host Host name to connect to, defaults to the pod IP. You probably want to set "Host" in
 *   httpHeaders instead.
 * @param httpHeaders Custom headers to set in the request. HTTP allows repeated headers.
 * @param path Path to access on the HTTP server.
 * @param port Name or number of the port to access on the container. Number must be in the range 1
 *   to 65535. Name must be an IANA_SVC_NAME.
 * @param scheme Scheme to use for connecting to the host. Defaults to HTTP.
 */
@Serializable
public data class HTTPGetAction(
    public val host: String,
    public val httpHeaders: List<HTTPHeader>,
    public val path: String,
    public val port: IntOrString,
    public val scheme: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "HTTPGetAction"
}
