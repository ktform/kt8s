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
 * @param fieldsType FieldsType is the discriminator for the different fields format and version.
 *   There is currently only one possible value: "FieldsV1"
 * @param fieldsV1 FieldsV1 holds the first JSON version format as described in the "FieldsV1" type.
 * @param manager Manager is an identifier of the workflow managing these fields.
 * @param operation Operation is the type of operation which lead to this ManagedFieldsEntry being
 *   created. The only valid values for this field are 'Apply' and 'Update'.
 * @param subresource Subresource is the name of the subresource used to update that object, or
 *   empty string if the object was updated through the main resource. The value of this field is
 *   used to distinguish between managers, even if they share the same name. For example, a status
 *   update will be distinct from a regular update using the same manager name. Note that the
 *   APIVersion field is not related to the Subresource field and it always corresponds to the
 *   version of the main resource.
 * @param time Time is the timestamp of when the ManagedFields entry was added. The timestamp will
 *   also be updated if a field is added, the manager changes any of the owned fields value or
 *   removes a field. The timestamp does not update when a field is removed from the entry because
 *   another manager took it over.
 */
@Serializable
public data class ManagedFieldsEntry(
    public val fieldsType: String,
    public val fieldsV1: RawJsonObject,
    public val manager: String,
    public val operation: String,
    public val subresource: String,
    public val time: KubernetesTime,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

    @Transient override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ManagedFieldsEntry"
}
