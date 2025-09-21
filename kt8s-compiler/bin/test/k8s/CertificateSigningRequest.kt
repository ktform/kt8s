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
 * @param spec spec contains the certificate request, and is immutable after creation. Only the
 *   request, signerName, expirationSeconds, and usages fields can be set on creation. Other fields
 *   are derived by Kubernetes and cannot be modified by users.
 * @param status status contains information about whether the request is approved or denied, and
 *   the certificate issued by the signer, or the failure condition indicating signer failure.
 */
@Serializable
public data class CertificateSigningRequest(
    public val metadata: ObjectMeta,
    public val spec: CertificateSigningRequestSpec,
    public val status: CertificateSigningRequestStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "certificates.k8s.io/v1"

    @Transient override val group: String = "certificates.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CertificateSigningRequest"
}
