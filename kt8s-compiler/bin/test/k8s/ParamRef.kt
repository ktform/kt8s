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
 * @param name name is the name of the resource being referenced.
 *
 * One of `name` or `selector` must be set, but `name` and `selector` are mutually exclusive
 * properties. If one is set, the other must be unset.
 *
 * A single parameter used for all admission requests can be configured by setting the `name` field,
 * leaving `selector` blank, and setting namespace if `paramKind` is namespace-scoped.
 *
 * @param namespace namespace is the namespace of the referenced resource. Allows limiting the
 *   search for params to a specific namespace. Applies to both `name` and `selector` fields.
 *
 * A per-namespace parameter may be used by specifying a namespace-scoped `paramKind` in the policy
 * and leaving this field empty.
 * - If `paramKind` is cluster-scoped, this field MUST be unset. Setting this field results in a
 *   configuration error.
 * - If `paramKind` is namespace-scoped, the namespace of the object being evaluated for admission
 *   will be used when this field is left unset. Take care that if this is left empty the binding
 *   must not match any cluster-scoped resources, which will result in an error.
 *
 * @param parameterNotFoundAction `parameterNotFoundAction` controls the behavior of the binding
 *   when the resource exists, and name or selector is valid, but there are no parameters matched by
 *   the binding. If the value is set to `Allow`, then no matched parameters will be treated as
 *   successful validation by the binding. If set to `Deny`, then no matched parameters will be
 *   subject to the `failurePolicy` of the policy.
 *
 * Allowed values are `Allow` or `Deny`
 *
 * Required
 *
 * @param selector selector can be used to match multiple param objects based on their labels.
 *   Supply selector: {} to match all resources of the ParamKind.
 *
 * If multiple params are found, they are all evaluated with the policy expressions and the results
 * are ANDed together.
 *
 * One of `name` or `selector` must be set, but `name` and `selector` are mutually exclusive
 * properties. If one is set, the other must be unset.
 */
@Serializable
public data class ParamRef(
    public val name: String,
    public val namespace: String,
    public val parameterNotFoundAction: String,
    public val selector: LabelSelector,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ParamRef"
}
