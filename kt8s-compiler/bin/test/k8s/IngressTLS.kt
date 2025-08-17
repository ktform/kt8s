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
 * @param hosts hosts is a list of hosts included in the TLS certificate. The values in this list
 *   must match the name/s used in the tlsSecret. Defaults to the wildcard host setting for the
 *   loadbalancer controller fulfilling this Ingress, if left unspecified.
 * @param secretName secretName is the name of the secret used to terminate TLS traffic on port 443.
 *   Field is left optional to allow TLS routing based on SNI hostname alone. If the SNI host in a
 *   listener conflicts with the "Host" header field used by an IngressRule, the SNI host is used
 *   for termination and value of the "Host" header is used for routing.
 */
@Serializable
public data class IngressTLS(public val hosts: List<String>, public val secretName: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "IngressTLS"
}
