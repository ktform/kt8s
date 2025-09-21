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
 * @param pullPolicy Policy for pulling OCI objects. Possible values are: Always: the kubelet always
 *   attempts to pull the reference. Container creation will fail If the pull fails. Never: the
 *   kubelet never pulls the reference and only uses a local image or artifact. Container creation
 *   will fail if the reference isn't present. IfNotPresent: the kubelet pulls if the reference
 *   isn't already present on disk. Container creation will fail if the reference isn't present and
 *   the pull fails. Defaults to Always if :latest tag is specified, or IfNotPresent otherwise.
 * @param reference Required: Image or artifact reference to be used. Behaves in the same way as
 *   pod.spec.containers[*].image. Pull secrets will be assembled in the same way as for the
 *   container image by looking up node credentials, SA image pull secrets, and pod spec image pull
 *   secrets. More info: https://kubernetes.io/docs/concepts/containers/images This field is
 *   optional to allow higher level config management to default or override container images in
 *   workload controllers like Deployments and StatefulSets.
 */
@Serializable
public data class ImageVolumeSource(public val pullPolicy: String, public val reference: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ImageVolumeSource"
}
