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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param mountPath Path within the container at which the volume should be mounted. Must not
 *   contain ':'.
 * @param mountPropagation mountPropagation determines how mounts are propagated from the host to
 *   container and the other way around. When not set, MountPropagationNone is used. This field is
 *   beta in 1.10. When RecursiveReadOnly is set to IfPossible or to Enabled, MountPropagation must
 *   be None or unspecified (which defaults to None).
 * @param name This must match the Name of a Volume.
 * @param readOnly Mounted read-only if true, read-write otherwise (false or unspecified). Defaults
 *   to false.
 * @param recursiveReadOnly RecursiveReadOnly specifies whether read-only mounts should be handled
 *   recursively.
 *
 * If ReadOnly is false, this field has no meaning and must be unspecified.
 *
 * If ReadOnly is true, and this field is set to Disabled, the mount is not made recursively
 * read-only. If this field is set to IfPossible, the mount is made recursively read-only, if it is
 * supported by the container runtime. If this field is set to Enabled, the mount is made
 * recursively read-only if it is supported by the container runtime, otherwise the pod will not be
 * started and an error will be generated to indicate the reason.
 *
 * If this field is set to IfPossible or Enabled, MountPropagation must be set to None (or be
 * unspecified, which defaults to None).
 *
 * If this field is not specified, it is treated as an equivalent of Disabled.
 *
 * @param subPath Path within the volume from which the container's volume should be mounted.
 *   Defaults to "" (volume's root).
 * @param subPathExpr Expanded path within the volume from which the container's volume should be
 *   mounted. Behaves similarly to SubPath but environment variable references $(VAR_NAME) are
 *   expanded using the container's environment. Defaults to "" (volume's root). SubPathExpr and
 *   SubPath are mutually exclusive.
 */
@Serializable
public data class VolumeMount(
    public val mountPath: String,
    public val mountPropagation: String,
    public val name: String,
    public val readOnly: Boolean,
    public val recursiveReadOnly: String,
    public val subPath: String,
    public val subPathExpr: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "VolumeMount"
}
