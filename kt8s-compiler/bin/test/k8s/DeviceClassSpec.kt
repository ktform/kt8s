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
 * @param config Config defines configuration parameters that apply to each device that is claimed
 *   via this class. Some classses may potentially be satisfied by multiple drivers, so each
 *   instance of a vendor configuration applies to exactly one driver.
 *
 * They are passed to the driver, but are not considered while allocating the claim.
 *
 * @param selectors Each selector must be satisfied by a device which is claimed via this class.
 */
@Serializable
public data class DeviceClassSpec(
    public val config: List<DeviceClassConfiguration>,
    public val selectors: List<DeviceSelector>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceClassSpec"
}
