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
 * @param jsonPath jsonPath is a simple JSON path which is evaluated against each custom resource to
 *   produce a field selector value. Only JSON paths without the array notation are allowed. Must
 *   point to a field of type string, boolean or integer. Types with enum values and strings with
 *   formats are allowed. If jsonPath refers to absent field in a resource, the jsonPath evaluates
 *   to an empty string. Must not point to metdata fields. Required.
 */
@Serializable
public data class SelectableField(public val jsonPath: String) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

    @Transient override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SelectableField"
}
