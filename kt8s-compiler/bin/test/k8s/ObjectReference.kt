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
 * @param fieldPath If referring to a piece of an object instead of an entire object, this string
 *   should contain a valid JSON/Go field access statement, such as
 *   desiredState.manifest.containers[2]. For example, if the object reference is to a container
 *   within a pod, this would take on a value like: "spec.containers{name}" (where "name" refers to
 *   the name of the container that triggered the event) or if no container name is specified
 *   "spec.containers[2]" (container with index 2 in this pod). This syntax is chosen only to have
 *   some well-defined way of referencing a part of an object.
 * @param name Name of the referent. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names
 * @param namespace Namespace of the referent. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/
 * @param resourceVersion Specific resourceVersion to which this reference is made, if any. More
 *   info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#concurrency-control-and-consistency
 * @param uid UID of the referent. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#uids
 */
@Serializable
public data class ObjectReference(
    public val fieldPath: String,
    public val name: String,
    public val namespace: String,
    public val resourceVersion: String,
    public val uid: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ObjectReference"
}
