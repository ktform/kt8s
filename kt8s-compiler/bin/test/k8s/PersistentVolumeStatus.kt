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
 * @param lastPhaseTransitionTime lastPhaseTransitionTime is the time the phase transitioned from
 *   one to another and automatically resets to current time everytime a volume phase transitions.
 * @param message message is a human-readable message indicating details about why the volume is in
 *   this state.
 * @param phase phase indicates if a volume is available, bound to a claim, or released by a claim.
 *   More info: https://kubernetes.io/docs/concepts/storage/persistent-volumes#phase
 * @param reason reason is a brief CamelCase string that describes any failure and is meant for
 *   machine parsing and tidy display in the CLI.
 */
@Serializable
public data class PersistentVolumeStatus(
    public val lastPhaseTransitionTime: KubernetesTime,
    public val message: String,
    public val phase: String,
    public val reason: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PersistentVolumeStatus"
}
