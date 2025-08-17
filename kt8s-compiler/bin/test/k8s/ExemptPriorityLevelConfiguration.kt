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
 * @param lendablePercent `lendablePercent` prescribes the fraction of the level's NominalCL that
 *   can be borrowed by other priority levels. This value of this field must be between 0 and 100,
 *   inclusive, and it defaults to 0. The number of seats that other levels can borrow from this
 *   level, known as this level's LendableConcurrencyLimit (LendableCL), is defined as follows.
 *
 * LendableCL(i) = round( NominalCL(i) * lendablePercent(i)/100.0 )
 *
 * @param nominalConcurrencyShares `nominalConcurrencyShares` (NCS) contributes to the computation
 *   of the NominalConcurrencyLimit (NominalCL) of this level. This is the number of execution seats
 *   nominally reserved for this priority level. This DOES NOT limit the dispatching from this
 *   priority level but affects the other priority levels through the borrowing mechanism. The
 *   server's concurrency limit (ServerCL) is divided among all the priority levels in proportion to
 *   their NCS values:
 *
 * NominalCL(i) = ceil( ServerCL * NCS(i) / sum_ncs ) sum_ncs = sum[priority level k] NCS(k)
 *
 * Bigger numbers mean a larger nominal concurrency limit, at the expense of every other priority
 * level. This field has a default value of zero.
 */
@Serializable
public data class ExemptPriorityLevelConfiguration(
    public val lendablePercent: Int,
    public val nominalConcurrencyShares: Int,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

    @Transient override val group: String = "io.k8s.api.flowcontrol"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ExemptPriorityLevelConfiguration"
}
