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
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param description description is an arbitrary string that usually provides guidelines on when
 *   this priority class should be used.
 * @param globalDefault globalDefault specifies whether this PriorityClass should be considered as
 *   the default priority for pods that do not have any priority class. Only one PriorityClass can
 *   be marked as `globalDefault`. However, if more than one PriorityClasses exists with their
 *   `globalDefault` field set to true, the smallest value of such global default PriorityClasses
 *   will be used as the default priority.
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param preemptionPolicy preemptionPolicy is the Policy for preempting pods with lower priority.
 *   One of Never, PreemptLowerPriority. Defaults to PreemptLowerPriority if unset.
 * @param value value represents the integer value of this priority class. This is the actual
 *   priority that pods receive when they have the name of this class in their pod spec.
 */
@Serializable
public data class PriorityClass(
    public val description: String,
    public val globalDefault: Boolean,
    public val metadata: ObjectMeta,
    public val preemptionPolicy: String,
    public val `value`: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "scheduling.k8s.io/v1"

    @Transient override val group: String = "scheduling.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PriorityClass"
}
