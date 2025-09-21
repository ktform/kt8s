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
package dev.ktform.kt8s.crds

object CassandraCrds : Crds {
  override val crdUrls: Map<String, String> = mapOf(
    "Datacenter.yaml" to "https://raw.githubusercontent.com/k8ssandra/k8ssandra/refs/heads/main/charts/cass-operator/crds/cassandradatacenters.yaml",
    "Task.yaml" to "https://raw.githubusercontent.com/k8ssandra/k8ssandra/refs/heads/main/charts/cass-operator/crds/cassandratasks.yaml",
    "ScheduledTask.yaml" to "https://raw.githubusercontent.com/k8ssandra/k8ssandra/refs/heads/main/charts/cass-operator/crds/scheduledtasks.yaml",
  )
}
