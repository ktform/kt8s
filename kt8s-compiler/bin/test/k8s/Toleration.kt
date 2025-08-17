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

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param effect Effect indicates the taint effect to match. Empty means match all taint effects.
 *   When specified, allowed values are NoSchedule, PreferNoSchedule and NoExecute.
 * @param key Key is the taint key that the toleration applies to. Empty means match all taint keys.
 *   If the key is empty, operator must be Exists; this combination means to match all values and
 *   all keys.
 * @param operator Operator represents a key's relationship to the value. Valid operators are Exists
 *   and Equal. Defaults to Equal. Exists is equivalent to wildcard for value, so that a pod can
 *   tolerate all taints of a particular category.
 * @param tolerationSeconds TolerationSeconds represents the period of time the toleration (which
 *   must be of effect NoExecute, otherwise this field is ignored) tolerates the taint. By default,
 *   it is not set, which means tolerate the taint forever (do not evict). Zero and negative values
 *   will be treated as 0 (evict immediately) by the system.
 * @param value Value is the taint value the toleration matches to. If the operator is Exists, the
 *   value should be empty, otherwise just a regular string.
 */
@Serializable
public data class Toleration(
    public val effect: String,
    public val key: String,
    public val `operator`: String,
    public val tolerationSeconds: Long,
    public val `value`: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Toleration"
}
