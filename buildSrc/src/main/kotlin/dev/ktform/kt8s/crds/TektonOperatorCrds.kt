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

object TektonOperatorCrds : Crds {
  override val crdUrls: Map<String, String> = mapOf(
    "Chain.yaml" to "https://raw.githubusercontent.com/tektoncd/operator/main/config/base/300-operator_v1alpha1_chain_crd.yaml",
    "Config.yaml" to "https://raw.githubusercontent.com/tektoncd/operator/main/config/base/300-operator_v1alpha1_config_crd.yaml",
    "OperatorHub.yaml" to "https://raw.githubusercontent.com/tektoncd/operator/main/config/base/300-operator_v1alpha1_hub_crd.yaml",
    "InstallerSet.yaml" to "https://raw.githubusercontent.com/tektoncd/operator/main/config/base/300-operator_v1alpha1_installer_set_crd.yaml",
    "OperatorPipeline.yaml" to "https://raw.githubusercontent.com/tektoncd/operator/main/config/base/300-operator_v1alpha1_pipeline_crd.yaml",
    "OperatorResult.yaml" to "https://raw.githubusercontent.com/tektoncd/operator/main/config/base/300-operator_v1alpha1_result_crd.yaml",
    "OperatorTrigger.yaml" to "https://raw.githubusercontent.com/tektoncd/operator/main/config/base/300-operator_v1alpha1_trigger_crd.yaml",
  )
}
