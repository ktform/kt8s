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
 * @param backend backend defines the referenced service endpoint to which the traffic will be
 *   forwarded to.
 * @param path path is matched against the path of an incoming request. Currently it can contain
 *   characters disallowed from the conventional "path" part of a URL as defined by RFC 3986. Paths
 *   must begin with a '/' and must be present when using PathType with value "Exact" or "Prefix".
 * @param pathType pathType determines the interpretation of the path matching. PathType can be one
 *   of the following values: * Exact: Matches the URL path exactly. * Prefix: Matches based on a
 *   URL path prefix split by '/'. Matching is done on a path element by element basis. A path
 *   element refers is the list of labels in the path split by the '/' separator. A request is a
 *   match for path p if every p is an element-wise prefix of p of the request path. Note that if
 *   the last element of the path is a substring of the last element in request path, it is not a
 *   match (e.g. /foo/bar matches /foo/bar/baz, but does not match /foo/barbaz).
 * * ImplementationSpecific: Interpretation of the Path matching is up to the IngressClass.
 *   Implementations can treat this as a separate PathType or treat it identically to Prefix or
 *   Exact path types. Implementations are required to support all path types.
 */
@Serializable
public data class HTTPIngressPath(
    public val backend: IngressBackend,
    public val path: String,
    public val pathType: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "HTTPIngressPath"
}
