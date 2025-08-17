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
 * @param metadata May contain labels and annotations that will be copied into the PVC when creating
 *   it. No other fields are allowed and will be rejected during validation.
 * @param spec The specification for the PersistentVolumeClaim. The entire content is copied
 *   unchanged into the PVC that gets created from this template. The same fields as in a
 *   PersistentVolumeClaim are also valid here.
 */
@Serializable
public data class PersistentVolumeClaimTemplate(
    public val metadata: ObjectMeta,
    public val spec: PersistentVolumeClaimSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PersistentVolumeClaimTemplate"
}
