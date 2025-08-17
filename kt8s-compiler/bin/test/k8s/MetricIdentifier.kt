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
 * @param name name is the name of the given metric
 * @param selector selector is the string-encoded form of a standard kubernetes label selector for
 *   the given metric When set, it is passed as an additional parameter to the metrics server for
 *   more specific metrics scoping. When unset, just the metricName will be used to gather metrics.
 */
@Serializable
public data class MetricIdentifier(public val name: String, public val selector: LabelSelector) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v2"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v2"

    @SerialName("kind") override val kind: String = "MetricIdentifier"
}
