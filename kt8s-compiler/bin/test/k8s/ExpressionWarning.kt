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
 * @param fieldRef The path to the field that refers the expression. For example, the reference to
 *   the expression of the first item of validations is "spec.validations[0].expression"
 * @param warning The content of type checking information in a human-readable form. Each line of
 *   the warning contains the type that the expression is checked against, followed by the type
 *   check error from the compiler.
 */
@Serializable
public data class ExpressionWarning(public val fieldRef: String, public val warning: String) :
    Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ExpressionWarning"
}
