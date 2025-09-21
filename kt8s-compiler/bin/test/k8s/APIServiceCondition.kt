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
 * @param lastTransitionTime Last time the condition transitioned from one status to another.
 * @param message Human-readable message indicating details about last transition.
 * @param reason Unique, one-word, CamelCase reason for the condition's last transition.
 * @param status Status is the status of the condition. Can be True, False, Unknown.
 * @param type Type is the type of the condition.
 */
@Serializable
public data class APIServiceCondition(
    public val lastTransitionTime: KubernetesTime,
    public val message: String,
    public val reason: String,
    public val status: String,
    public val type: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.kube-aggregator.pkg.apis.apiregistration/v1"

    @Transient override val group: String = "io.k8s.kube-aggregator.pkg.apis.apiregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "APIServiceCondition"
}
