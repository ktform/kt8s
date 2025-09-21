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
 * @param driver Driver is used to determine which kubelet plugin needs to be passed these
 *   configuration parameters.
 *
 * An admission policy provided by the driver developer could use this to decide whether it needs to
 * validate them.
 *
 * Must be a DNS subdomain and should end with a DNS domain owned by the vendor of the driver.
 *
 * @param parameters Parameters can contain arbitrary data. It is the responsibility of the driver
 *   developer to handle validation and versioning. Typically this includes self-identification and
 *   a version ("kind" + "apiVersion" for Kubernetes types), with conversion between different
 *   versions.
 *
 * The length of the raw data must be smaller or equal to 10 Ki.
 */
@Serializable
public data class OpaqueDeviceConfiguration(
    public val driver: String,
    public val parameters: RawJsonObject,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "OpaqueDeviceConfiguration"
}
