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
 * @param start start is the number representing the first replica's index. It may be used to number
 *   replicas from an alternate index (eg: 1-indexed) over the default 0-indexed names, or to
 *   orchestrate progressive movement of replicas from one StatefulSet to another. If set, replica
 *   indices will be in the range: [.spec.ordinals.start, .spec.ordinals.start + .spec.replicas). If
 *   unset, defaults to 0. Replica indices will be in the range: [0, .spec.replicas).
 */
@Serializable
public data class StatefulSetOrdinals(public val start: Int) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "StatefulSetOrdinals"
}
