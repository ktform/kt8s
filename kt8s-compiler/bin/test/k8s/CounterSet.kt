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
 * @param counters Counters defines the counters that will be consumed by the device. The name of
 *   each counter must be unique in that set and must be a DNS label.
 *
 * To ensure this uniqueness, capacities defined by the vendor must be listed without the driver
 * name as domain prefix in their name. All others must be listed with their domain prefix.
 *
 * The maximum number of counters is 32.
 *
 * @param name CounterSet is the name of the set from which the counters defined will be consumed.
 */
@Serializable
public data class CounterSet(public val counters: Counter, public val name: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "CounterSet"
}
