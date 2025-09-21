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

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param distinguisherMethod `distinguisherMethod` defines how to compute the flow distinguisher
 *   for requests that match this schema. `nil` specifies that the distinguisher is disabled and
 *   thus will always be the empty string.
 * @param matchingPrecedence `matchingPrecedence` is used to choose among the FlowSchemas that match
 *   a given request. The chosen FlowSchema is among those with the numerically lowest (which we
 *   take to be logically highest) MatchingPrecedence. Each MatchingPrecedence value must be ranged
 *   in [1,10000]. Note that if the precedence is not specified, it will be set to 1000 as default.
 * @param priorityLevelConfiguration `priorityLevelConfiguration` should reference a
 *   PriorityLevelConfiguration in the cluster. If the reference cannot be resolved, the FlowSchema
 *   will be ignored and marked as invalid in its status. Required.
 * @param rules `rules` describes which requests will match this flow schema. This FlowSchema
 *   matches a request if and only if at least one member of rules matches the request. if it is an
 *   empty slice, there will be no requests matching the FlowSchema.
 */
@Serializable
public data class FlowSchemaSpec(
    public val distinguisherMethod: FlowDistinguisherMethod,
    public val matchingPrecedence: Int,
    public val priorityLevelConfiguration: PriorityLevelConfigurationReference,
    public val rules: List<PolicyRulesWithSubjects>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

    @Transient override val group: String = "io.k8s.api.flowcontrol"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "FlowSchemaSpec"
}
