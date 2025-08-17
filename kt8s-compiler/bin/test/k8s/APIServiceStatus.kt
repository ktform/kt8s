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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/** @param conditions Current service state of apiService. */
@Serializable
public data class APIServiceStatus(public val conditions: List<APIServiceCondition>) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.kube-aggregator.pkg.apis.apiregistration/v1"

    @Transient override val group: String = "io.k8s.kube-aggregator.pkg.apis.apiregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "APIServiceStatus"
}
