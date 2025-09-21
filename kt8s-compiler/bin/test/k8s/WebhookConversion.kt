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
 * @param clientConfig clientConfig is the instructions for how to call the webhook if strategy is
 *   `Webhook`.
 * @param conversionReviewVersions conversionReviewVersions is an ordered list of preferred
 *   `ConversionReview` versions the Webhook expects. The API server will use the first version in
 *   the list which it supports. If none of the versions specified in this list are supported by API
 *   server, conversion will fail for the custom resource. If a persisted Webhook configuration
 *   specifies allowed versions and does not include any versions known to the API Server, calls to
 *   the webhook will fail.
 */
@Serializable
public data class WebhookConversion(
    public val clientConfig: WebhookClientConfig,
    public val conversionReviewVersions: List<String>,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

    @Transient override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "WebhookConversion"
}
