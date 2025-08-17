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
 * @param caBundle `caBundle` is a PEM encoded CA bundle which will be used to validate the
 *   webhook's server certificate. If unspecified, system trust roots on the apiserver are used.
 * @param service `service` is a reference to the service for this webhook. Either `service` or
 *   `url` must be specified.
 *
 * If the webhook is running within the cluster, then you should use `service`.
 *
 * @param url `url` gives the location of the webhook, in standard URL form
 *   (`scheme://host:port/path`). Exactly one of `url` or `service` must be specified.
 *
 * The `host` should not refer to a service running in the cluster; use the `service` field instead.
 * The host might be resolved via external DNS in some apiservers (e.g., `kube-apiserver` cannot
 * resolve in-cluster DNS as that would be a layering violation). `host` may also be an IP address.
 *
 * Please note that using `localhost` or `127.0.0.1` as a `host` is risky unless you take great care
 * to run this webhook on all hosts which run an apiserver which might need to make calls to this
 * webhook. Such installs are likely to be non-portable, i.e., not easy to turn up in a new cluster.
 *
 * The scheme must be "https"; the URL must begin with "https://".
 *
 * A path is optional, and if present may be any string permissible in a URL. You may use the path
 * to pass an arbitrary string to the webhook, for example, a cluster identifier.
 *
 * Attempting to use a user or basic auth e.g. "user:password@" is not allowed. Fragments ("#...")
 * and query parameters ("?...") are not allowed, either.
 */
@Serializable
public data class WebhookClientConfig(
    public val caBundle: String,
    public val service: ServiceReference,
    public val url: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "WebhookClientConfig"
}
