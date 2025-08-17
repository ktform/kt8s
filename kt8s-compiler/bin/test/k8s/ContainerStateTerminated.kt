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
 * @param containerID Container's ID in the format '<type>://<container_id>'
 * @param exitCode Exit status from the last termination of the container
 * @param finishedAt Time at which the container last terminated
 * @param message Message regarding the last termination of the container
 * @param reason (brief) reason from the last termination of the container
 * @param signal Signal from the last termination of the container
 * @param startedAt Time at which previous execution of the container started
 */
@Serializable
public data class ContainerStateTerminated(
    public val containerID: String,
    public val exitCode: Int,
    public val finishedAt: KubernetesTime,
    public val message: String,
    public val reason: String,
    public val signal: Int,
    public val startedAt: KubernetesTime,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ContainerStateTerminated"
}
