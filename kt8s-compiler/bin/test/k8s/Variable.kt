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
 * @param expression Expression is the expression that will be evaluated as the value of the
 *   variable. The CEL expression has access to the same identifiers as the CEL expressions in
 *   Validation.
 * @param name Name is the name of the variable. The name must be a valid CEL identifier and unique
 *   among all variables. The variable can be accessed in other expressions through `variables` For
 *   example, if name is "foo", the variable will be available as `variables.foo`
 */
@Serializable
public data class Variable(public val expression: String, public val name: String) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Variable"
}
