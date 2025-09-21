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
 * @param nameservers A list of DNS name server IP addresses. This will be appended to the base
 *   nameservers generated from DNSPolicy. Duplicated nameservers will be removed.
 * @param options A list of DNS resolver options. This will be merged with the base options
 *   generated from DNSPolicy. Duplicated entries will be removed. Resolution options given in
 *   Options will override those that appear in the base DNSPolicy.
 * @param searches A list of DNS search domains for host-name lookup. This will be appended to the
 *   base search paths generated from DNSPolicy. Duplicated search paths will be removed.
 */
@Serializable
public data class PodDNSConfig(
    public val nameservers: List<String>,
    public val options: List<PodDNSConfigOption>,
    public val searches: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PodDNSConfig"
}
