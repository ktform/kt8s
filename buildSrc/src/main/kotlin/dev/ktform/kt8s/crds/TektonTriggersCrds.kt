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

object TektonTriggersCrds : Crds {
  override val crdUrls: Map<String, String> = mapOf(
    "ClusterInterceptor.yaml" to "https://raw.githubusercontent.com/tektoncd/triggers/main/config/300-clusterinterceptor.yaml",
    "ClusterTriggerBinding.yaml" to "https://raw.githubusercontent.com/tektoncd/triggers/main/config/300-clustertriggerbinding.yaml",
    "EventListener.yaml" to "https://raw.githubusercontent.com/tektoncd/triggers/main/config/300-eventlistener.yaml",
    "Interceptor.yaml" to "https://raw.githubusercontent.com/tektoncd/triggers/main/config/300-interceptor.yaml",
    "Trigger.yaml" to "https://raw.githubusercontent.com/tektoncd/triggers/main/config/300-trigger.yaml",
    "TriggerBinding.yaml" to "https://raw.githubusercontent.com/tektoncd/triggers/main/config/300-triggerbinding.yaml",
    "TriggerTemplate.yaml" to "https://raw.githubusercontent.com/tektoncd/triggers/main/config/300-triggertemplate.yaml",
  )
}