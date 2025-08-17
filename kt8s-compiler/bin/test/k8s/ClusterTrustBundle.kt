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
 * @param metadata metadata contains the object metadata.
 * @param spec spec contains the signer (if any) and trust anchors.
 */
@Serializable
public data class ClusterTrustBundle(
    public val metadata: ObjectMeta,
    public val spec: ClusterTrustBundleSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "certificates.k8s.io/v1alpha1"

    @Transient override val group: String = "certificates.k8s.io"

    @Transient override val version: String = "v1alpha1"

    @SerialName("kind") override val kind: String = "ClusterTrustBundle"
}
