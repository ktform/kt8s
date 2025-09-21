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
 * @param metadata Standard object's metadata. metadata.name must be the Kubernetes node name.
 * @param spec spec is the specification of CSINode
 */
@Serializable
public data class CSINode(public val metadata: ObjectMeta, public val spec: CSINodeSpec) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "storage.k8s.io/v1"

    @Transient override val group: String = "storage.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CSINode"
}
