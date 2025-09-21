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
 * @param succeededCount succeededCount specifies the minimal required size of the actual set of the
 *   succeeded indexes for the Job. When succeededCount is used along with succeededIndexes, the
 *   check is constrained only to the set of indexes specified by succeededIndexes. For example,
 *   given that succeededIndexes is "1-4", succeededCount is "3", and completed indexes are "1",
 *   "3", and "5", the Job isn't declared as succeeded because only "1" and "3" indexes are
 *   considered in that rules. When this field is null, this doesn't default to any value and is
 *   never evaluated at any time. When specified it needs to be a positive integer.
 * @param succeededIndexes succeededIndexes specifies the set of indexes which need to be contained
 *   in the actual set of the succeeded indexes for the Job. The list of indexes must be within 0 to
 *   ".spec.completions-1" and must not contain duplicates. At least one element is required. The
 *   indexes are represented as intervals separated by commas. The intervals can be a decimal
 *   integer or a pair of decimal integers separated by a hyphen. The number are listed in
 *   represented by the first and last element of the series, separated by a hyphen. For example, if
 *   the completed indexes are 1, 3, 4, 5 and 7, they are represented as "1,3-5,7". When this field
 *   is null, this field doesn't default to any value and is never evaluated at any time.
 */
@Serializable
public data class SuccessPolicyRule(
    public val succeededCount: Int,
    public val succeededIndexes: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.batch/v1"

    @Transient override val group: String = "io.k8s.api.batch"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SuccessPolicyRule"
}
