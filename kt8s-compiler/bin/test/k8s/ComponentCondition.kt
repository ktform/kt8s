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
 * @param error Condition error code for a component. For example, a health check error code.
 * @param message Message about the condition for a component. For example, information about a
 *   health check.
 * @param status Status of the condition for a component. Valid values for "Healthy": "True",
 *   "False", or "Unknown".
 * @param type Type of condition for a component. Valid value: "Healthy"
 */
@Serializable
public data class ComponentCondition(
    public val error: String,
    public val message: String,
    public val status: String,
    public val type: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ComponentCondition"
}
