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
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec Spec contains information for locating and communicating with a server
 * @param status Status contains derived information about an API server
 */
@Serializable
public data class APIService(
    public val metadata: ObjectMeta,
    public val spec: APIServiceSpec,
    public val status: APIServiceStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "apiregistration.k8s.io/v1"

    @Transient override val group: String = "apiregistration.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "APIService"
}
