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
 * @param strategy strategy specifies how custom resources are converted between versions. Allowed
 *   values are: - `"None"`: The converter only change the apiVersion and would not touch any other
 *   field in the custom resource. - `"Webhook"`: API Server will call to an external webhook to do
 *   the conversion. Additional information is needed for this option. This requires
 *   spec.preserveUnknownFields to be false, and spec.conversion.webhook to be set.
 * @param webhook webhook describes how to call the conversion webhook. Required when `strategy` is
 *   set to `"Webhook"`.
 */
@Serializable
public data class CustomResourceConversion(
    public val strategy: String,
    public val webhook: WebhookConversion,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

    @Transient override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CustomResourceConversion"
}
