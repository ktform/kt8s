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
 * @param acceptedNames acceptedNames are the names that are actually being used to serve discovery.
 *   They may be different than the names in spec.
 * @param conditions conditions indicate state for particular aspects of a CustomResourceDefinition
 * @param storedVersions storedVersions lists all versions of CustomResources that were ever
 *   persisted. Tracking these versions allows a migration path for stored versions in etcd. The
 *   field is mutable so a migration controller can finish a migration to another version (ensuring
 *   no old objects are left in storage), and then remove the rest of the versions from this list.
 *   Versions may not be removed from `spec.versions` while they exist in this list.
 */
@Serializable
public data class CustomResourceDefinitionStatus(
    public val acceptedNames: CustomResourceDefinitionNames,
    public val conditions: List<CustomResourceDefinitionCondition>,
    public val storedVersions: List<String>,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

    @Transient override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CustomResourceDefinitionStatus"
}
