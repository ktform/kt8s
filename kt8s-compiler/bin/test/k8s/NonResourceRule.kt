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
 * @param nonResourceURLs NonResourceURLs is a set of partial urls that a user should have access
 *   to. *s are allowed, but only as the full, final step in the path. "*" means all.
 * @param verbs Verb is a list of kubernetes non-resource API verbs, like: get, post, put, delete,
 *   patch, head, options. "*" means all.
 */
@Serializable
public data class NonResourceRule(
    public val nonResourceURLs: List<String>,
    public val verbs: List<String>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.authorization/v1"

    @Transient override val group: String = "io.k8s.api.authorization"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NonResourceRule"
}
