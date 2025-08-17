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
 * @param configMapKeyRef Selects a key of a ConfigMap.
 * @param fieldRef Selects a field of the pod: supports metadata.name, metadata.namespace,
 *   `metadata.labels['<KEY>']`, `metadata.annotations['<KEY>']`, spec.nodeName,
 *   spec.serviceAccountName, status.hostIP, status.podIP, status.podIPs.
 * @param resourceFieldRef Selects a resource of the container: only resources limits and requests
 *   (limits.cpu, limits.memory, limits.ephemeral-storage, requests.cpu, requests.memory and
 *   requests.ephemeral-storage) are currently supported.
 * @param secretKeyRef Selects a key of a secret in the pod's namespace
 */
@Serializable
public data class EnvVarSource(
    public val configMapKeyRef: ConfigMapKeySelector,
    public val fieldRef: ObjectFieldSelector,
    public val resourceFieldRef: ResourceFieldSelector,
    public val secretKeyRef: SecretKeySelector,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "EnvVarSource"
}
