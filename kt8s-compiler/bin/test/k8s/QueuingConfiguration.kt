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

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param handSize `handSize` is a small positive number that configures the shuffle sharding of
 *   requests into queues. When enqueuing a request at this priority level the request's flow
 *   identifier (a string pair) is hashed and the hash value is used to shuffle the list of queues
 *   and deal a hand of the size specified here. The request is put into one of the shortest queues
 *   in that hand. `handSize` must be no larger than `queues`, and should be significantly smaller
 *   (so that a few heavy flows do not saturate most of the queues). See the user-facing
 *   documentation for more extensive guidance on setting this field. This field has a default value
 *   of 8.
 * @param queueLengthLimit `queueLengthLimit` is the maximum number of requests allowed to be
 *   waiting in a given queue of this priority level at a time; excess requests are rejected. This
 *   value must be positive. If not specified, it will be defaulted to 50.
 * @param queues `queues` is the number of queues for this priority level. The queues exist
 *   independently at each apiserver. The value must be positive. Setting it to 1 effectively
 *   precludes shufflesharding and thus makes the distinguisher method of associated flow schemas
 *   irrelevant. This field has a default value of 64.
 */
@Serializable
public data class QueuingConfiguration(
    public val handSize: Int,
    public val queueLengthLimit: Int,
    public val queues: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

    @Transient override val group: String = "io.k8s.api.flowcontrol"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "QueuingConfiguration"
}
