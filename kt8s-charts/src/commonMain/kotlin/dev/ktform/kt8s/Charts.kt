package dev.ktform.kt8s

import MinioChart
import arrow.core.Option
import arrow.core.toOption
import dev.ktform.kt8s.charts.compute.*
import dev.ktform.kt8s.charts.development.DexChart
import dev.ktform.kt8s.charts.development.GiteaChart
import dev.ktform.kt8s.charts.development.TheiaChart
import dev.ktform.kt8s.charts.finops.OpenCostChart
import dev.ktform.kt8s.charts.gitops.ArgoCDChart
import dev.ktform.kt8s.charts.gitops.ArgoRolloutsChart
import dev.ktform.kt8s.charts.gitops.ArgoWorkflowsChart
import dev.ktform.kt8s.charts.gitops.TektonChainsChart
import dev.ktform.kt8s.charts.gitops.TektonDashboardChart
import dev.ktform.kt8s.charts.gitops.TektonPipelineChart
import dev.ktform.kt8s.charts.gitops.TektonResultsChart
import dev.ktform.kt8s.charts.gitops.TektonTriggersChart
import dev.ktform.kt8s.charts.mlops.DataFusionBallistaChart
import dev.ktform.kt8s.charts.mlops.FeastChart
import dev.ktform.kt8s.charts.mlops.KubeFlinkChart
import dev.ktform.kt8s.charts.mlops.KubeRayChart
import dev.ktform.kt8s.charts.mlops.VolcanoChart
import dev.ktform.kt8s.charts.networking.CiliumChart
import dev.ktform.kt8s.charts.networking.CorazaChart
import dev.ktform.kt8s.charts.networking.ExternalDNSChart
import dev.ktform.kt8s.charts.observability.AlloyChart
import dev.ktform.kt8s.charts.observability.GrafanaChart
import dev.ktform.kt8s.charts.observability.LokiChart
import dev.ktform.kt8s.charts.observability.MimirChart
import dev.ktform.kt8s.charts.observability.PrometheusChart
import dev.ktform.kt8s.charts.observability.TempoChart
import dev.ktform.kt8s.charts.security.CertManagerChart
import dev.ktform.kt8s.charts.security.ExternalSecretsChart
import dev.ktform.kt8s.charts.security.FalcoChart
import dev.ktform.kt8s.charts.security.OpenBaoChart
import dev.ktform.kt8s.charts.storage.LocalPathProvisionerChart
import dev.ktform.kt8s.charts.storage.PVCAutoresizerChart
import dev.ktform.kt8s.charts.storage.RookChart
import dev.ktform.kt8s.charts.storage.ScyllaDBChart
import dev.ktform.kt8s.charts.storage.TopoLVMChart
import dev.ktform.kt8s.charts.storage.VeleroChart
import dev.ktform.kt8s.charts.storage.postgres.CNPGChart
import dev.ktform.kt8s.charts.storage.postgres.StackgresChart

enum class Charts(
  private val chart: Chart,
  private val chartWithVersion: (String, ChartGroup) -> Chart,
) {
  // Compute
  Descheduler(
    DeschedulerChart(Versions.descheduler, ChartGroup.Compute),
    ::DeschedulerChart,
  ),
  Goldilocks(
    GoldilocksChart(Versions.goldilocks, ChartGroup.Compute),
    ::GoldilocksChart,
  ),
  Keda(
    KedaChart(Versions.keda, ChartGroup.Compute),
    ::KedaChart,
  ),
  Karpenter(
    KarpenterChart(Versions.karpenter, ChartGroup.Compute),
    ::KarpenterChart,
  ),
  VPA(
    VPAChart(Versions.vpa, ChartGroup.Compute),
    ::VPAChart,
  ),

  // Development
  Dex(DexChart(Versions.dex, ChartGroup.Development), ::DexChart),
  Gitea(GiteaChart(Versions.gitea, ChartGroup.Development), ::GiteaChart),
  Theia(TheiaChart(Versions.theia, ChartGroup.Development), ::TheiaChart),

  // FinOps
  OpenCost(OpenCostChart(Versions.openCost, ChartGroup.FinOps), ::OpenCostChart),

  // GitOps
  ArgoCD(ArgoCDChart(Versions.argoCd, ChartGroup.GitOps), ::ArgoCDChart),
  ArgoRollouts(ArgoRolloutsChart(Versions.argoRollouts, ChartGroup.GitOps), ::ArgoRolloutsChart),
  ArgoWorkflows(ArgoWorkflowsChart(Versions.argoWorkflows, ChartGroup.GitOps), ::ArgoWorkflowsChart),
  TektonChains(TektonChainsChart(Versions.tektonChains, ChartGroup.GitOps), ::TektonChainsChart),
  TektonDashboard(
    TektonDashboardChart(Versions.tektonDashboard, ChartGroup.GitOps),
    ::TektonDashboardChart),
  TektonPipeline(
    TektonPipelineChart(Versions.tektonPipeline, ChartGroup.GitOps), ::TektonPipelineChart
  ),
  TektonResults(TektonResultsChart(Versions.tektonResults, ChartGroup.GitOps), ::TektonResultsChart),
  TektonTriggers(TektonTriggersChart(Versions.tektonTriggers, ChartGroup.GitOps), ::TektonTriggersChart),

  // MlOps
  DataFusionBallista(DataFusionBallistaChart(Versions.dataFusionBallista, ChartGroup.MlOps), ::DataFusionBallistaChart),
  Feast(FeastChart(Versions.feast, ChartGroup.MlOps), ::FeastChart),
  KubeFlink(KubeFlinkChart(Versions.kubeFlink, ChartGroup.MlOps), ::KubeFlinkChart),
  KubeRay(KubeRayChart(Versions.kubeRay, ChartGroup.MlOps), ::KubeRayChart),
  Volcano(VolcanoChart(Versions.volcano, ChartGroup.MlOps), ::VolcanoChart),

  // Networking
  Cilium(CiliumChart(Versions.cilium, ChartGroup.Networking), ::CiliumChart),
  Coraza(CorazaChart(Versions.coraza, ChartGroup.Networking), ::CorazaChart),
  ExternalDNS(ExternalDNSChart(Versions.externaldns, ChartGroup.Networking), ::ExternalDNSChart),

  // Observability
  Alloy(AlloyChart(Versions.alloy, ChartGroup.Observability), ::AlloyChart),
  Grafana(GrafanaChart(Versions.grafana, ChartGroup.Observability), ::GrafanaChart),
  Loki(LokiChart(Versions.loki, ChartGroup.Observability), ::LokiChart),
  Mimir(MimirChart(Versions.mimir, ChartGroup.Observability), ::MimirChart),
  Prometheus(PrometheusChart(Versions.prometheus, ChartGroup.Observability), ::PrometheusChart),
  Tempo(TempoChart(Versions.tempo, ChartGroup.Observability), ::TempoChart),

  // Security
  CertManager(CertManagerChart(Versions.certManager, ChartGroup.Security), ::CertManagerChart),
  ExternalSecrets(ExternalSecretsChart(Versions.externalSecrets, ChartGroup.Security), ::ExternalSecretsChart),
  Falco(FalcoChart(Versions.falco, ChartGroup.Security), ::FalcoChart),
  OpenBao(OpenBaoChart(Versions.openBao, ChartGroup.Security), ::OpenBaoChart),

  // Storage
  Minio(MinioChart(Versions.minio, ChartGroup.Storage), ::MinioChart),
  CNPGChart(CNPGChart(Versions.cnpg, ChartGroup.Storage), ::CNPGChart),
  StackgresChart(StackgresChart(Versions.stackgres, ChartGroup.Storage), ::StackgresChart),
  LocalPathProvisioner(LocalPathProvisionerChart(Versions.localpathprovisioner, ChartGroup.Storage), ::LocalPathProvisionerChart),
  PVCAutoresizerChart(PVCAutoresizerChart(Versions.pvcAutoresizer, ChartGroup.Storage), ::PVCAutoresizerChart),
  Rook(RookChart(Versions.rook, ChartGroup.Storage), ::RookChart),
  ScyllaDB(ScyllaDBChart(Versions.scyllaDB, ChartGroup.Storage), ::ScyllaDBChart),
  TopoLVM(TopoLVMChart(Versions.topoLvm, ChartGroup.Storage), ::TopoLVMChart),
  Velero(VeleroChart(Versions.velero, ChartGroup.Storage), ::VeleroChart),
  ;

  internal fun withVersion(version: String): Chart = chartWithVersion(version, chart.group)

  companion object {
    fun groups(): Map<ChartGroup, List<Chart>> =
      ChartGroup.entries.associateWith { group -> Charts.entries.filter { it.chart.group == group }.map { it.chart } }

    internal fun applyOverrides(name: String): Option<Chart> =
      entries.find {
        it.name.equals(name, ignoreCase = true)
      }.toOption().map { it.chart }

    internal fun overrideVersion(name: String, version: String): Option<Chart> =
      entries.find {
        it.name.equals(name, ignoreCase = true)
      }?.withVersion(version).toOption()
  }
}