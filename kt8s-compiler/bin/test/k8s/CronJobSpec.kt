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
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param concurrencyPolicy Specifies how to treat concurrent executions of a Job. Valid values are:
 * - "Allow" (default): allows CronJobs to run concurrently; - "Forbid": forbids concurrent runs,
 *   skipping next run if previous run hasn't finished yet; - "Replace": cancels currently running
 *   job and replaces it with a new one
 *
 * @param failedJobsHistoryLimit The number of failed finished jobs to retain. Value must be
 *   non-negative integer. Defaults to 1.
 * @param jobTemplate Specifies the job that will be created when executing a CronJob.
 * @param schedule The schedule in Cron format, see https://en.wikipedia.org/wiki/Cron.
 * @param startingDeadlineSeconds Optional deadline in seconds for starting the job if it misses
 *   scheduled time for any reason. Missed jobs executions will be counted as failed ones.
 * @param successfulJobsHistoryLimit The number of successful finished jobs to retain. Value must be
 *   non-negative integer. Defaults to 3.
 * @param suspend This flag tells the controller to suspend subsequent executions, it does not apply
 *   to already started executions. Defaults to false.
 * @param timeZone The time zone name for the given schedule, see
 *   https://en.wikipedia.org/wiki/List_of_tz_database_time_zones. If not specified, this will
 *   default to the time zone of the kube-controller-manager process. The set of valid time zone
 *   names and the time zone offset is loaded from the system-wide time zone database by the API
 *   server during CronJob validation and the controller manager during execution. If no system-wide
 *   time zone database can be found a bundled version of the database is used instead. If the time
 *   zone name becomes invalid during the lifetime of a CronJob or due to a change in host
 *   configuration, the controller will stop creating new new Jobs and will create a system event
 *   with the reason UnknownTimeZone. More information can be found in
 *   https://kubernetes.io/docs/concepts/workloads/controllers/cron-jobs/#time-zones
 */
@Serializable
public data class CronJobSpec(
    public val concurrencyPolicy: String,
    public val failedJobsHistoryLimit: Int,
    public val jobTemplate: JobTemplateSpec,
    public val schedule: String,
    public val startingDeadlineSeconds: Long,
    public val successfulJobsHistoryLimit: Int,
    public val `suspend`: Boolean,
    public val timeZone: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.batch/v1"

    @Transient override val group: String = "io.k8s.api.batch"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "CronJobSpec"
}
