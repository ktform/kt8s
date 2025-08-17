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
 * @param field The field of the resource that has caused this error, as named by its JSON
 *   serialization. May include dot and postfix notation for nested attributes. Arrays are
 *   zero-indexed. Fields may appear more than once in an array of causes due to fields having
 *   multiple errors. Optional.
 *
 * Examples: "name" - the field "name" on the current resource "items[0].name" - the field "name" on
 * the first array entry in "items"
 *
 * @param message A human-readable description of the cause of the error. This field may be
 *   presented as-is to a reader.
 * @param reason A machine-readable description of the cause of the error. If this value is empty
 *   there is no information available.
 */
@Serializable
public data class StatusCause(
    public val `field`: String,
    public val message: String,
    public val reason: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

    @Transient override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "StatusCause"
}
