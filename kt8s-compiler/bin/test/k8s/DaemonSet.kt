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
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec The desired behavior of this daemon set. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status
 * @param status The current status of this daemon set. This data may be out of date by some window
 *   of time. Populated by the system. Read-only. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status
 */
@Serializable
public data class DaemonSet(
    public val metadata: ObjectMeta,
    public val spec: DaemonSetSpec,
    public val status: DaemonSetStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "apps/v1"

    @Transient override val group: String = "apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "DaemonSet"
}
