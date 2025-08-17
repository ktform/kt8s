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
 * @param metadata The name is <group>.<resource>.
 * @param spec Spec is an empty spec. It is here to comply with Kubernetes API style.
 * @param status API server instances report the version they can decode and the version they encode
 *   objects to when persisting objects in the backend.
 */
@Serializable
public data class StorageVersion(
    public val metadata: ObjectMeta,
    public val spec: RawJsonObject,
    public val status: StorageVersionStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "internal.apiserver.k8s.io/v1alpha1"

    @Transient override val group: String = "internal.apiserver.k8s.io"

    @Transient override val version: String = "v1alpha1"

    @SerialName("kind") override val kind: String = "StorageVersion"
}
