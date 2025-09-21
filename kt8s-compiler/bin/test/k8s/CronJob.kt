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
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec Specification of the desired behavior of a cron job, including the schedule. More
 *   info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status
 * @param status Current status of a cron job. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status
 */
@Serializable
public data class CronJob(
    public val metadata: ObjectMeta,
    public val spec: CronJobSpec,
    public val status: CronJobStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "batch/v1"

    @Transient override val group: String = "batch"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CronJob"
}
