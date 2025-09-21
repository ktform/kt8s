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

import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param automountServiceAccountToken AutomountServiceAccountToken indicates whether pods running
 *   as this service account should have an API token automatically mounted. Can be overridden at
 *   the pod level.
 * @param imagePullSecrets ImagePullSecrets is a list of references to secrets in the same namespace
 *   to use for pulling any images in pods that reference this ServiceAccount. ImagePullSecrets are
 *   distinct from Secrets because Secrets can be mounted in the pod, but ImagePullSecrets are only
 *   accessed by the kubelet. More info:
 *   https://kubernetes.io/docs/concepts/containers/images/#specifying-imagepullsecrets-on-a-pod
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param secrets Secrets is a list of the secrets in the same namespace that pods running using
 *   this ServiceAccount are allowed to use. Pods are only limited to this list if this service
 *   account has a "kubernetes.io/enforce-mountable-secrets" annotation set to "true". The
 *   "kubernetes.io/enforce-mountable-secrets" annotation is deprecated since v1.32. Prefer separate
 *   namespaces to isolate access to mounted secrets. This field should not be used to find
 *   auto-generated service account token secrets for use outside of pods. Instead, tokens can be
 *   requested directly using the TokenRequest API, or service account token secrets can be manually
 *   created. More info: https://kubernetes.io/docs/concepts/configuration/secret
 */
@Serializable
public data class ServiceAccount(
    public val automountServiceAccountToken: Boolean,
    public val imagePullSecrets: List<LocalObjectReference>,
    public val metadata: ObjectMeta,
    public val secrets: List<ObjectReference>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "v1"

    @Transient override val group: String = ""

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ServiceAccount"
}
