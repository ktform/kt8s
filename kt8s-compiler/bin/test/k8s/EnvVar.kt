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
 * @param name Name of the environment variable. Must be a C_IDENTIFIER.
 * @param value Variable references $(VAR_NAME) are expanded using the previously defined
 *   environment variables in the container and any service environment variables. If a variable
 *   cannot be resolved, the reference in the input string will be unchanged. Double $$ are reduced
 *   to a single $, which allows for escaping the $(VAR_NAME) syntax: i.e. "$$(VAR_NAME)" will
 *   produce the string literal "$(VAR_NAME)". Escaped references will never be expanded, regardless
 *   of whether the variable exists or not. Defaults to "".
 * @param valueFrom Source for the environment variable's value. Cannot be used if value is not
 *   empty.
 */
@Serializable
public data class EnvVar(
    public val name: String,
    public val `value`: String,
    public val valueFrom: EnvVarSource,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "EnvVar"
}
