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
 * @param applyConfiguration applyConfiguration defines the desired configuration values of an
 *   object. The configuration is applied to the admission object using
 *   [structured merge diff](https://github.com/kubernetes-sigs/structured-merge-diff). A CEL
 *   expression is used to create apply configuration.
 * @param jsonPatch jsonPatch defines a [JSON patch](https://jsonpatch.com/) operation to perform a
 *   mutation to the object. A CEL expression is used to create the JSON patch.
 * @param patchType patchType indicates the patch strategy used. Allowed values are
 *   "ApplyConfiguration" and "JSONPatch". Required.
 */
@Serializable
public data class Mutation(
    public val applyConfiguration: ApplyConfiguration,
    public val jsonPatch: JSONPatch,
    public val patchType: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1alpha1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1alpha1"

    @SerialName("kind") override val kind: String = "Mutation"
}
