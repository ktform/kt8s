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
 * @param clusterTrustBundle ClusterTrustBundle allows a pod to access the `.spec.trustBundle` field
 *   of ClusterTrustBundle objects in an auto-updating file.
 *
 * Alpha, gated by the ClusterTrustBundleProjection feature gate.
 *
 * ClusterTrustBundle objects can either be selected by name, or by the combination of signer name
 * and a label selector.
 *
 * Kubelet performs aggressive normalization of the PEM contents written into the pod filesystem.
 * Esoteric PEM features such as inter-block comments and block headers are stripped. Certificates
 * are deduplicated. The ordering of certificates within the file is arbitrary, and Kubelet may
 * change the order over time.
 *
 * @param configMap configMap information about the configMap data to project
 * @param downwardAPI downwardAPI information about the downwardAPI data to project
 * @param secret secret information about the secret data to project
 * @param serviceAccountToken serviceAccountToken is information about the serviceAccountToken data
 *   to project
 */
@Serializable
public data class VolumeProjection(
    public val clusterTrustBundle: ClusterTrustBundleProjection,
    public val configMap: ConfigMapProjection,
    public val downwardAPI: DownwardAPIProjection,
    public val secret: SecretProjection,
    public val serviceAccountToken: ServiceAccountTokenProjection,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VolumeProjection"
}
