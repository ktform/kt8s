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
 * @param resource resource is an ObjectRef to another Kubernetes resource in the namespace of the
 *   Ingress object. If resource is specified, a service.Name and service.Port must not be
 *   specified. This is a mutually exclusive setting with "Service".
 * @param service service references a service as a backend. This is a mutually exclusive setting
 *   with "Resource".
 */
@Serializable
public data class IngressBackend(
    public val resource: TypedLocalObjectReference,
    public val service: IngressServiceBackend,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "IngressBackend"
}
