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

object TektonPipelineCrds : Crds {
  override val crdUrls: Map<String, String> = mapOf(
    "CustomRun.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-customrun.yaml",
    "Pipeline.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-pipeline.yaml",
    "PipelineRun.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-pipelinerun.yaml",
    "ResolutionRequest.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-resolutionrequest.yaml",
    "StepAction.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-stepaction.yaml",
    "Task.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-task.yaml",
    "TaskRun.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-taskrun.yaml",
    "VerificationPolicy.yaml" to "https://raw.githubusercontent.com/tektoncd/pipeline/main/config/300-crds/300-verificationpolicy.yaml",
  )
}
