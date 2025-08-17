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
 * @param localhostProfile localhostProfile indicates a profile loaded on the node that should be
 *   used. The profile must be preconfigured on the node to work. Must match the loaded name of the
 *   profile. Must be set if and only if type is "Localhost".
 * @param type type indicates which kind of AppArmor profile will be applied. Valid options are:
 *   Localhost - a profile pre-loaded on the node. RuntimeDefault - the container runtime's default
 *   profile. Unconfined - no AppArmor enforcement.
 */
@Serializable
public data class AppArmorProfile(public val localhostProfile: String, public val type: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "AppArmorProfile"
}
