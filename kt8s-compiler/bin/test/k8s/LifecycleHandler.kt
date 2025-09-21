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
 * @param exec Exec specifies a command to execute in the container.
 * @param httpGet HTTPGet specifies an HTTP GET request to perform.
 * @param sleep Sleep represents a duration that the container should sleep.
 * @param tcpSocket Deprecated. TCPSocket is NOT supported as a LifecycleHandler and kept for
 *   backward compatibility. There is no validation of this field and lifecycle hooks will fail at
 *   runtime when it is specified.
 */
@Serializable
public data class LifecycleHandler(
    public val exec: ExecAction,
    public val httpGet: HTTPGetAction,
    public val sleep: SleepAction,
    public val tcpSocket: TCPSocketAction,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "LifecycleHandler"
}
