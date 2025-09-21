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
 * @param queuing `queuing` holds the configuration parameters for queuing. This field may be
 *   non-empty only if `type` is `"Queue"`.
 * @param type `type` is "Queue" or "Reject". "Queue" means that requests that can not be executed
 *   upon arrival are held in a queue until they can be executed or a queuing limit is reached.
 *   "Reject" means that requests that can not be executed upon arrival are rejected. Required.
 */
@Serializable
public data class LimitResponse(public val queuing: QueuingConfiguration, public val type: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

    @Transient override val group: String = "io.k8s.api.flowcontrol"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "LimitResponse"
}
