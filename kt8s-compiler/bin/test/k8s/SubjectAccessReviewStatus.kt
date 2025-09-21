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
 * @param allowed Allowed is required. True if the action would be allowed, false otherwise.
 * @param denied Denied is optional. True if the action would be denied, otherwise false. If both
 *   allowed is false and denied is false, then the authorizer has no opinion on whether to
 *   authorize the action. Denied may not be true if Allowed is true.
 * @param evaluationError EvaluationError is an indication that some error occurred during the
 *   authorization check. It is entirely possible to get an error and be able to continue determine
 *   authorization status in spite of it. For instance, RBAC can be missing a role, but enough roles
 *   are still present and bound to reason about the request.
 * @param reason Reason is optional. It indicates why a request was allowed or denied.
 */
@Serializable
public data class SubjectAccessReviewStatus(
    public val allowed: Boolean,
    public val denied: Boolean,
    public val evaluationError: String,
    public val reason: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.authorization/v1"

    @Transient override val group: String = "io.k8s.api.authorization"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SubjectAccessReviewStatus"
}
