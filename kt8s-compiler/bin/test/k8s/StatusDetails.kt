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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param causes The Causes array includes more details associated with the StatusReason failure.
 *   Not all StatusReasons may provide detailed causes.
 * @param name The name attribute of the resource associated with the status StatusReason (when
 *   there is a single name which can be described).
 * @param retryAfterSeconds If specified, the time in seconds before the operation should be
 *   retried. Some errors may indicate the client must take an alternate action - for those errors
 *   this field may indicate how long to wait before taking the alternate action.
 * @param uid UID of the resource. (when there is a single resource which can be described). More
 *   info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names#uids
 */
@Serializable
public data class StatusDetails(
    public val causes: List<StatusCause>,
    public val name: String,
    public val retryAfterSeconds: Int,
    public val uid: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

    @Transient override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "StatusDetails"
}
