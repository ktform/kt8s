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
 * @param name Name is the name of the object being referenced.
 * @param namespace Namespace is the namespace of the object being referenced.
 * @param resource Resource is the resource of the object being referenced.
 */
@Serializable
public data class ParentReference(
    public val name: String,
    public val namespace: String,
    public val resource: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ParentReference"
}
