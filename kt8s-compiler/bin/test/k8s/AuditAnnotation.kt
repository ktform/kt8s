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
 * @param key key specifies the audit annotation key. The audit annotation keys of a
 *   ValidatingAdmissionPolicy must be unique. The key must be a qualified name
 *   ([A-Za-z0-9][-A-Za-z0-9_.]*) no more than 63 bytes in length.
 *
 * The key is combined with the resource name of the ValidatingAdmissionPolicy to construct an audit
 * annotation key: "{ValidatingAdmissionPolicy name}/{key}".
 *
 * If an admission webhook uses the same resource name as this ValidatingAdmissionPolicy and the
 * same audit annotation key, the annotation key will be identical. In this case, the first
 * annotation written with the key will be included in the audit event and all subsequent
 * annotations with the same key will be discarded.
 *
 * Required.
 *
 * @param valueExpression valueExpression represents the expression which is evaluated by CEL to
 *   produce an audit annotation value. The expression must evaluate to either a string or null
 *   value. If the expression evaluates to a string, the audit annotation is included with the
 *   string value. If the expression evaluates to null or empty string the audit annotation will be
 *   omitted. The valueExpression may be no longer than 5kb in length. If the result of the
 *   valueExpression is more than 10kb in length, it will be truncated to 10kb.
 *
 * If multiple ValidatingAdmissionPolicyBinding resources match an API request, then the
 * valueExpression will be evaluated for each binding. All unique values produced by the
 * valueExpressions will be joined together in a comma-separated list.
 *
 * Required.
 */
@Serializable
public data class AuditAnnotation(public val key: String, public val valueExpression: String) :
    Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "AuditAnnotation"
}
