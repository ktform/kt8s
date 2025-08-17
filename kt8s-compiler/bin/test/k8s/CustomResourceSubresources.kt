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
 * @param scale scale indicates the custom resource should serve a `/scale` subresource that returns
 *   an `autoscaling/v1` Scale object.
 * @param status status indicates the custom resource should serve a `/status` subresource. When
 *   enabled: 1. requests to the custom resource primary endpoint ignore changes to the `status`
 *   stanza of the object. 2. requests to the custom resource `/status` subresource ignore changes
 *   to anything other than the `status` stanza of the object.
 */
@Serializable
public data class CustomResourceSubresources(
    public val scale: CustomResourceSubresourceScale,
    public val status: RawJsonObject,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

    @Transient override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CustomResourceSubresources"
}
