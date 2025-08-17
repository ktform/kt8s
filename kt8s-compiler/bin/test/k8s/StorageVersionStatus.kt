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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param commonEncodingVersion If all API server instances agree on the same encoding storage
 *   version, then this field is set to that version. Otherwise this field is left empty. API
 *   servers should finish updating its storageVersionStatus entry before serving write operations,
 *   so that this field will be in sync with the reality.
 * @param conditions The latest available observations of the storageVersion's state.
 * @param storageVersions The reported versions per API server instance.
 */
@Serializable
public data class StorageVersionStatus(
    public val commonEncodingVersion: String,
    public val conditions: List<StorageVersionCondition>,
    public val storageVersions: List<ServerStorageVersion>,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.apiserverinternal/v1alpha1"

    @Transient override val group: String = "io.k8s.api.apiserverinternal"

    @Transient override val version: String = "v1alpha1"

    @SerialName("kind") override val kind: String = "StorageVersionStatus"
}
