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
 * @param replicas replicas is the actual number of observed instances of the scaled object.
 * @param selector selector is the label query over pods that should match the replicas count. This
 *   is same as the label selector but in the string format to avoid introspection by clients. The
 *   string will be in the same format as the query-param syntax. More info about label selectors:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/
 */
@Serializable
public data class ScaleStatus(public val replicas: Int, public val selector: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.autoscaling/v1"

    @Transient override val group: String = "io.k8s.api.autoscaling"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ScaleStatus"
}
