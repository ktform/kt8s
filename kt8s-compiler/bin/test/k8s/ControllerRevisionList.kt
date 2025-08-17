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
 * @param items Items is the list of ControllerRevisions
 * @param metadata More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 */
@Serializable
public data class ControllerRevisionList(
    public val items: List<ControllerRevision>,
    public val metadata: ListMeta,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "apps/v1"

    @Transient override val group: String = "apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ControllerRevisionList"
}
