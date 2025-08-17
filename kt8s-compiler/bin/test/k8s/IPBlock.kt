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

/**
 * @param cidr cidr is a string representing the IPBlock Valid examples are "192.168.1.0/24" or
 *   "2001:db8::/64"
 * @param except except is a slice of CIDRs that should not be included within an IPBlock Valid
 *   examples are "192.168.1.0/24" or "2001:db8::/64" Except values will be rejected if they are
 *   outside the cidr range
 */
@Serializable
public data class IPBlock(public val cidr: String, public val except: List<String>) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "IPBlock"
}
