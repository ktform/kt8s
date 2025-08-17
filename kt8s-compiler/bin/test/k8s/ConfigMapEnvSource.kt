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

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param name Name of the referent. This field is effectively required, but due to backwards
 *   compatibility is allowed to be empty. Instances of this type with an empty value here are
 *   almost certainly wrong. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names
 * @param optional Specify whether the ConfigMap must be defined
 */
@Serializable
public data class ConfigMapEnvSource(public val name: String, public val optional: Boolean) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ConfigMapEnvSource"
}
