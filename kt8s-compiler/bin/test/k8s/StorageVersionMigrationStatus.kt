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
 * @param conditions The latest available observations of the migration's current state.
 * @param resourceVersion ResourceVersion to compare with the GC cache for performing the migration.
 *   This is the current resource version of given group, version and resource when
 *   kube-controller-manager first observes this StorageVersionMigration resource.
 */
@Serializable
public data class StorageVersionMigrationStatus(
    public val conditions: List<MigrationCondition>,
    public val resourceVersion: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.storagemigration/v1alpha1"

    @Transient override val group: String = "io.k8s.api.storagemigration"

    @Transient override val version: String = "v1alpha1"

    @SerialName("kind") override val kind: String = "StorageVersionMigrationStatus"
}
