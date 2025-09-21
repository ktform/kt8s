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
 * @param kubeletConfigKey KubeletConfigKey declares which key of the referenced ConfigMap
 *   corresponds to the KubeletConfiguration structure This field is required in all cases.
 * @param name Name is the metadata.name of the referenced ConfigMap. This field is required in all
 *   cases.
 * @param namespace Namespace is the metadata.namespace of the referenced ConfigMap. This field is
 *   required in all cases.
 * @param resourceVersion ResourceVersion is the metadata.ResourceVersion of the referenced
 *   ConfigMap. This field is forbidden in Node.Spec, and required in Node.Status.
 * @param uid UID is the metadata.UID of the referenced ConfigMap. This field is forbidden in
 *   Node.Spec, and required in Node.Status.
 */
@Serializable
public data class ConfigMapNodeConfigSource(
    public val kubeletConfigKey: String,
    public val name: String,
    public val namespace: String,
    public val resourceVersion: String,
    public val uid: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ConfigMapNodeConfigSource"
}
