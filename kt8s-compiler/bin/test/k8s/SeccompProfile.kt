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
 * @param localhostProfile localhostProfile indicates a profile defined in a file on the node should
 *   be used. The profile must be preconfigured on the node to work. Must be a descending path,
 *   relative to the kubelet's configured seccomp profile location. Must be set if type is
 *   "Localhost". Must NOT be set for any other type.
 * @param type type indicates which kind of seccomp profile will be applied. Valid options are:
 *
 * Localhost - a profile defined in a file on the node should be used. RuntimeDefault - the
 * container runtime default profile should be used. Unconfined - no profile should be applied.
 */
@Serializable
public data class SeccompProfile(public val localhostProfile: String, public val type: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SeccompProfile"
}
