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
 * @param object Object is:
 *     * If Type is Added or Modified: the new state of the object.
 *     * If Type is Deleted: the state of the object immediately before deletion.
 *     * If Type is Error: *Status is recommended; other types may make sense depending on context.
 */
@Serializable
public data class WatchEvent(public val `object`: RawJsonObject, public val type: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "v1"

    @Transient override val group: String = ""

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "WatchEvent"
}
