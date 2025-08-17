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
 * @param nodeAffinity Describes node affinity scheduling rules for the pod.
 * @param podAffinity Describes pod affinity scheduling rules (e.g. co-locate this pod in the same
 *   node, zone, etc. as some other pod(s)).
 * @param podAntiAffinity Describes pod anti-affinity scheduling rules (e.g. avoid putting this pod
 *   in the same node, zone, etc. as some other pod(s)).
 */
@Serializable
public data class Affinity(
    public val nodeAffinity: NodeAffinity,
    public val podAffinity: PodAffinity,
    public val podAntiAffinity: PodAntiAffinity,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Affinity"
}
