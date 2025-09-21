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
 * @param audiences Audiences is a list of the identifiers that the resource server presented with
 *   the token identifies as. Audience-aware token authenticators will verify that the token was
 *   intended for at least one of the audiences in this list. If no audiences are provided, the
 *   audience will default to the audience of the Kubernetes apiserver.
 * @param token Token is the opaque bearer token.
 */
@Serializable
public data class TokenReviewSpec(public val audiences: List<String>, public val token: String) :
    Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.authentication/v1"

    @Transient override val group: String = "io.k8s.api.authentication"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "TokenReviewSpec"
}
