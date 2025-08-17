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
 * @param handler handler specifies the underlying runtime and configuration that the CRI
 *   implementation will use to handle pods of this class. The possible values are specific to the
 *   node & CRI configuration. It is assumed that all handlers are available on every node, and
 *   handlers of the same name are equivalent on every node. For example, a handler called "runc"
 *   might specify that the runc OCI runtime (using native Linux containers) will be used to run the
 *   containers in a pod. The Handler must be lowercase, conform to the DNS Label (RFC 1123)
 *   requirements, and is immutable.
 * @param metadata More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param overhead overhead represents the resource overhead associated with running a pod for a
 *   given RuntimeClass. For more details, see
 *   https://kubernetes.io/docs/concepts/scheduling-eviction/pod-overhead/
 * @param scheduling scheduling holds the scheduling constraints to ensure that pods running with
 *   this RuntimeClass are scheduled to nodes that support it. If scheduling is nil, this
 *   RuntimeClass is assumed to be supported by all nodes.
 */
@Serializable
public data class RuntimeClass(
    public val handler: String,
    public val metadata: ObjectMeta,
    public val overhead: Overhead,
    public val scheduling: Scheduling,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "node.k8s.io/v1"

    @Transient override val group: String = "node.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "RuntimeClass"
}
