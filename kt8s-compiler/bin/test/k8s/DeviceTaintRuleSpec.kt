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
 * @param deviceSelector DeviceSelector defines which device(s) the taint is applied to. All
 *   selector criteria must be satified for a device to match. The empty selector matches all
 *   devices. Without a selector, no devices are matches.
 * @param taint The taint that gets applied to matching devices.
 */
@Serializable
public data class DeviceTaintRuleSpec(
    public val deviceSelector: DeviceTaintSelector,
    public val taint: DeviceTaint,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceTaintRuleSpec"
}
