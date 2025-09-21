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
 * @param metadata Standard object metadata. metadata.Name indicates the name of the CSI driver that
 *   this object refers to; it MUST be the same name returned by the CSI GetPluginName() call for
 *   that driver. The driver name must be 63 characters or less, beginning and ending with an
 *   alphanumeric character ([a-z0-9A-Z]) with dashes (-), dots (.), and alphanumerics between. More
 *   info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec spec represents the specification of the CSI Driver.
 */
@Serializable
public data class CSIDriver(public val metadata: ObjectMeta, public val spec: CSIDriverSpec) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "storage.k8s.io/v1"

    @Transient override val group: String = "storage.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CSIDriver"
}
