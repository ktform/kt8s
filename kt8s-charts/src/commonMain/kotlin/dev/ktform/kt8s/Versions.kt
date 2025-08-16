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

package dev.ktform.kt8s

import dev.ktform.kt8s.container.packages.compute.*
import dev.ktform.kt8s.container.packages.development.*
import dev.ktform.kt8s.container.packages.finops.*
import dev.ktform.kt8s.container.packages.gitops.*
import dev.ktform.kt8s.container.packages.mlops.*
import dev.ktform.kt8s.container.packages.networking.*
import dev.ktform.kt8s.container.packages.observability.*
import dev.ktform.kt8s.container.packages.security.*
import dev.ktform.kt8s.container.packages.storage.*
import dev.ktform.kt8s.container.packages.storage.postgres.*

import kotlin.properties.ReadOnlyProperty

object Versions {
  val latestVersions = mapOf(
    // Compute
    "clusterautoscaler" to ClusterAutoscaler.latest,
    "descheduler" to Descheduler.latest,
    "goldilocks" to Goldilocks.latest,
    "karpenter" to Karpenter.latest,
    "keda" to Keda.latest,
    "vpa" to VPA.latest,

    // Development
    "dex" to Dex.latest,
    "gitea" to Gitea.latest,
    "theia" to Theia.latest,
    "openCost" to OpenCost.latest,

    // GitOps
    "argoCd" to ArgoCD.latest,
    "argoRollouts" to ArgoRollouts.latest,
    "argoWorkflows" to ArgoWorkflows.latest,
    "tektonChains" to TektonChains.latest,
    "tektonDashboard" to TektonDashboard.latest,
    "tektonPipeline" to TektonPipeline.latest,
    "tektonResults" to TektonResults.latest,
    "tektonTriggers" to TektonTriggers.latest,

    // MlOps
    "dataFusionBallista" to DataFusionBallista.latest,
    "feast" to Feast.latest,
    "kubeFlink" to KubeFlink.latest,
    "kubeRay" to KubeRay.latest,
    "volcano" to Volcano.latest,

    // Networking
    "cilium" to Cilium.latest,
    "coraza" to Coraza.latest,
    "externaldns" to ExternalDNS.latest,

    // Observability
    "alloy" to Alloy.latest,
    "grafana" to Grafana.latest,
    "loki" to Loki.latest,
    "mimir" to Mimir.latest,
    "prometheus" to Prometheus.latest,
    "tempo" to Tempo.latest,

    // Security
    "certManager" to CertManager.latest,
    "externalSecrets" to ExternalSecrets.latest,
    "falco" to Falco.latest,
    "openBao" to OpenBao.latest,
    "kyverno" to Kyverno.latest,

    // Storage
    "minio" to Minio.latest,
    "cnpg" to CNPG.latest,
    "stackgres" to Stackgres.latest,
    "localpathprovisioner" to LocalPathProvisioner.latest,
    "pvcAutoresizer" to PVCAutoresizer.latest,
    "rook" to Rook.latest,
    "scylladb" to ScyllaDB.latest,
    "topolvm" to TopoLVM.latest,
    "velero" to Velero.latest,
  )

  private fun version() = ReadOnlyProperty<Any?, String> { _, property ->
    latestVersions[property.name.lowercase()]
      ?: error("Version not found for ${property.name}")
  }

  // Compute
  val clusterAutoscaler by version()
  val descheduler by version()
  val goldilocks by version()
  val karpenter by version()
  val keda by version()
  val vpa by version()

  // Development
  val dex by version()
  val gitea by version()
  val theia by version()

  // FinOps
  val openCost by version()

  // GitOps
  val argoCd by version()
  val argoRollouts by version()
  val argoWorkflows by version()
  val tektonChains by version()
  val tektonDashboard by version()
  val tektonPipeline by version()
  val tektonResults by version()
  val tektonTriggers by version()

  // MlOps
  val dataFusionBallista by version()
  val feast by version()
  val kubeFlink by version()
  val kubeRay by version()
  val volcano by version()

  // Networking
  val cilium by version()
  val coraza by version()
  val externaldns by version()

  // Observability
  val alloy by version()
  val grafana by version()
  val loki by version()
  val mimir by version()
  val prometheus by version()
  val tempo by version()

  // Security
  val certManager by version()
  val externalSecrets by version()
  val falco by version()
  val openBao by version()

  // Storage
  val minio by version()
  val cnpg by version()
  val stackgres by version()
  val localpathprovisioner by version()
  val pvcAutoresizer by version()
  val rook by version()
  val scyllaDB by version()
  val topoLvm by version()
  val velero by version()
}

