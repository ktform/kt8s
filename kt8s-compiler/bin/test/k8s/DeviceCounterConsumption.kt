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
 * @param counterSet CounterSet defines the set from which the counters defined will be consumed.
 * @param counters Counters defines the Counter that will be consumed by the device.
 *
 * The maximum number counters in a device is 32. In addition, the maximum number of all counters in
 * all devices is 1024 (for example, 64 devices with 16 counters each).
 */
@Serializable
public data class DeviceCounterConsumption(
    public val counterSet: String,
    public val counters: Counter,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceCounterConsumption"
}
