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
 * @param metadata Standard object metadata
 * @param spec Spec specifies the selector and one taint.
 *
 * Changing the spec automatically increments the metadata.generation number.
 */
@Serializable
public data class DeviceTaintRule(
    public val metadata: ObjectMeta,
    public val spec: DeviceTaintRuleSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "resource.k8s.io/v1alpha3"

    @Transient override val group: String = "resource.k8s.io"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceTaintRule"
}
