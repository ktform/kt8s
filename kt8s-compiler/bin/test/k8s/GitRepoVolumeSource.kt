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
 * @param directory directory is the target directory name. Must not contain or start with '..'. If
 *   '.' is supplied, the volume directory will be the git repository. Otherwise, if specified, the
 *   volume will contain the git repository in the subdirectory with the given name.
 * @param repository repository is the URL
 * @param revision revision is the commit hash for the specified revision.
 */
@Serializable
public data class GitRepoVolumeSource(
    public val directory: String,
    public val repository: String,
    public val revision: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "GitRepoVolumeSource"
}
