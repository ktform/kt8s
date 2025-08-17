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

import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param audience audience is the intended audience of the token. A recipient of a token must
 *   identify itself with an identifier specified in the audience of the token, and otherwise should
 *   reject the token. The audience defaults to the identifier of the apiserver.
 * @param expirationSeconds expirationSeconds is the requested duration of validity of the service
 *   account token. As the token approaches expiration, the kubelet volume plugin will proactively
 *   rotate the service account token. The kubelet will start trying to rotate the token if the
 *   token is older than 80 percent of its time to live or if the token is older than 24
 *   hours.Defaults to 1 hour and must be at least 10 minutes.
 * @param path path is the path relative to the mount point of the file to project the token into.
 */
@Serializable
public data class ServiceAccountTokenProjection(
    public val audience: String,
    public val expirationSeconds: Long,
    public val path: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ServiceAccountTokenProjection"
}
