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
 * @param status Status is filled in by the server with the user attributes.
 */
@Serializable
public data class SelfSubjectReview(
    public val metadata: ObjectMeta,
    public val status: SelfSubjectReviewStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "authentication.k8s.io/v1"

    @Transient override val group: String = "authentication.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SelfSubjectReview"
}
