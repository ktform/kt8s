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
 * @param forNodes forNodes indicates the node(s) this endpoint should be consumed by when using
 *   topology aware routing. May contain a maximum of 8 entries. This is an Alpha feature and is
 *   only used when the PreferSameTrafficDistribution feature gate is enabled.
 * @param forZones forZones indicates the zone(s) this endpoint should be consumed by when using
 *   topology aware routing. May contain a maximum of 8 entries.
 */
@Serializable
public data class EndpointHints(
    public val forNodes: List<ForNode>,
    public val forZones: List<ForZone>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.discovery/v1"

    @Transient override val group: String = "io.k8s.api.discovery"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "EndpointHints"
}
