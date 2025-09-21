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
 * @param exempt `exempt` specifies how requests are handled for an exempt priority level. This
 *   field MUST be empty if `type` is `"Limited"`. This field MAY be non-empty if `type` is
 *   `"Exempt"`. If empty and `type` is `"Exempt"` then the default values for
 *   `ExemptPriorityLevelConfiguration` apply.
 * @param limited `limited` specifies how requests are handled for a Limited priority level. This
 *   field must be non-empty if and only if `type` is `"Limited"`.
 * @param type `type` indicates whether this priority level is subject to limitation on request
 *   execution. A value of `"Exempt"` means that requests of this priority level are not subject to
 *   a limit (and thus are never queued) and do not detract from the capacity made available to
 *   other priority levels. A value of `"Limited"` means that (a) requests of this priority level
 *   _are_ subject to limits and (b) some of the server's limited capacity is made available
 *   exclusively to this priority level. Required.
 */
@Serializable
public data class PriorityLevelConfigurationSpec(
    public val exempt: ExemptPriorityLevelConfiguration,
    public val limited: LimitedPriorityLevelConfiguration,
    public val type: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

    @Transient override val group: String = "io.k8s.api.flowcontrol"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PriorityLevelConfigurationSpec"
}
