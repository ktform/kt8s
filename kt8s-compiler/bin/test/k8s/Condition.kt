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

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param lastTransitionTime lastTransitionTime is the last time the condition transitioned from one
 *   status to another. This should be when the underlying condition changed. If that is not known,
 *   then using the time when the API field changed is acceptable.
 * @param message message is a human readable message indicating details about the transition. This
 *   may be an empty string.
 * @param observedGeneration observedGeneration represents the .metadata.generation that the
 *   condition was set based upon. For instance, if .metadata.generation is currently 12, but the
 *   .status.conditions[x].observedGeneration is 9, the condition is out of date with respect to the
 *   current state of the instance.
 * @param reason reason contains a programmatic identifier indicating the reason for the condition's
 *   last transition. Producers of specific condition types may define expected values and meanings
 *   for this field, and whether the values are considered a guaranteed API. The value should be a
 *   CamelCase string. This field may not be empty.
 * @param status status of the condition, one of True, False, Unknown.
 * @param type type of condition in CamelCase or in foo.example.com/CamelCase.
 */
@Serializable
public data class Condition(
    public val lastTransitionTime: KubernetesTime,
    public val message: String,
    public val observedGeneration: Long,
    public val reason: String,
    public val status: String,
    public val type: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

    @Transient override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Condition"
}
