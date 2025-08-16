package dev.ktform.kt8s.container

import arrow.core.Either
import dev.ktform.kt8s.container.fetchers.*
import dev.ktform.kt8s.container.github.GithubClient
import io.github.z4kn4fein.semver.toVersion
import kotlin.collections.plus

sealed class Versions<T: Versions<T>>(val versions: Map<Component<T>, String>) {
  sealed interface Component<T: Versions<T>> {
    val name: String
  }

  interface VersionsFetcher<T: Versions<T>> {
    suspend fun getVersions(last: Int = 3): Map<Component<T>, List<String>>

    fun repo(component: Component<T>): String

    fun String.toRepoVersion(component: Component<T>): String

    fun Component<T>.knownLatestVersions(): List<String>

    companion object {
      suspend fun githubVersions(repo: String, prefix: String = "v", asIs: Boolean = false): Either<String, List<String>> {
        val client = GithubClient()

        return client.getTags(repo)
          .map { all ->
            val trimmed = all.map { it.removePrefix(prefix).trim() }

            if (asIs) {
              trimmed.sortedDescending()
            } else {
              trimmed
                .mapNotNull { s -> Either.catch { s.toVersion() }.getOrNull() }
                .filter { v -> !v.isPreRelease && (v.major == 0 || v.isStable) }
                .sortedDescending()
                .map { it.toString() }
            }
          }
      }

      fun String.withVPrefix() = "v$this"
    }
  }

  enum class KarpenterComponent: Component<KarpenterVersion> {
    Karpenter,
    AWS,
    AWSNodeTerminatorController,
    GCP,
    Azure
  }

  data class KarpenterVersion(val karpenterVersions: Map<KarpenterComponent, String>): Versions<KarpenterVersion>(karpenterVersions.mapKeys { it.key as Component<KarpenterVersion> }) {
    fun plus(other: KarpenterVersion): KarpenterVersion = KarpenterVersion(
      karpenterVersions + other.karpenterVersions
    )

    companion object: VersionsFetcher<KarpenterVersion> by KarpenterVersionFetcher {
      fun String.toVersion(component: KarpenterComponent): KarpenterVersion = KarpenterVersion(
        mapOf(
          component to this,
        )
      )
    }
  }

  enum class DeschedulerComponent: Component<DeschedulerVersion> {
    Descheduler
  }

  data class DeschedulerVersion(val deschedulerVersions: Map<DeschedulerComponent, String>): Versions<DeschedulerVersion>(deschedulerVersions.mapKeys { it.key as Component<DeschedulerVersion> }) {
    companion object: VersionsFetcher<DeschedulerVersion> by DeschedulerVersionFetcher {
      fun String.toDeschedulerVersion(): DeschedulerVersion = DeschedulerVersion(
        mapOf(
          DeschedulerComponent.Descheduler to this,
        )
      )
    }
  }

  enum class GoldilocksComponent: Component<GoldilocksVersion> {
    Goldilocks
  }

  data class GoldilocksVersion(val goldilocksVersions: Map<GoldilocksComponent, String>): Versions<GoldilocksVersion>(goldilocksVersions.mapKeys { it.key as Component<GoldilocksVersion> }) {
    companion object: VersionsFetcher<GoldilocksVersion> by GoldilocksVersionFetcher {
      fun String.toGoldilocksVersion(): GoldilocksVersion = GoldilocksVersion(
        mapOf(
          GoldilocksComponent.Goldilocks to this,
        )
      )
    }
  }

  enum class KedaComponent: Component<KedaVersion> {
    Keda
  }

  data class KedaVersion(val kedaVersions: Map<KedaComponent, String>): Versions<KedaVersion>(kedaVersions.mapKeys { it.key as Component<KedaVersion> }) {
    companion object: VersionsFetcher<KedaVersion> by KedaVersionFetcher {
      fun String.toKedaVersion(): KedaVersion = KedaVersion(
        mapOf(
          KedaComponent.Keda to this,
        )
      )
    }
  }

  enum class VPAComponent: Component<VPAVersion> {
    VPA
  }

  data class VPAVersion(val vpaVersions: Map<VPAComponent, String>): Versions<VPAVersion>(vpaVersions.mapKeys { it.key as Component<VPAVersion> }) {
    companion object: VersionsFetcher<VPAVersion> by VpaVersionFetcher {
      fun String.toVpaVersion(): VPAVersion = VPAVersion(
        mapOf(
          VPAComponent.VPA to this,
        )
      )
    }
  }

  enum class DexComponent: Component<DexVersion> {
    Dex
  }

  data class DexVersion(val dexVersions: Map<DexComponent, String>): Versions<DexVersion>(dexVersions.mapKeys { it.key as Component<DexVersion> }) {
    companion object: VersionsFetcher<DexVersion> by DexVersionFetcher {
      fun String.toDexVersion(): DexVersion = DexVersion(
        mapOf(
          DexComponent.Dex to this,
        )
      )
    }
  }

  enum class GiteaComponent: Component<GiteaVersion> {
    Gitea
  }

  data class GiteaVersion(val giteaVersions: Map<GiteaComponent, String>): Versions<GiteaVersion>(giteaVersions.mapKeys { it.key as Component<GiteaVersion> }) {
    companion object: VersionsFetcher<GiteaVersion> by GiteaVersionFetcher {
      fun String.toGiteaVersion(): GiteaVersion = GiteaVersion(
        mapOf(
          GiteaComponent.Gitea to this,
        )
      )
    }
  }

  enum class TheiaComponent: Component<TheiaVersion> {
    Theia
  }

  data class TheiaVersion(val theiaVersions: Map<TheiaComponent, String>): Versions<TheiaVersion>(theiaVersions.mapKeys { it.key as Component<TheiaVersion> }) {
    companion object: VersionsFetcher<TheiaVersion> by TheiaVersionFetcher {
      fun String.toTheiaVersion(): TheiaVersion = TheiaVersion(
        mapOf(
          TheiaComponent.Theia to this,
        )
      )
    }
  }

  enum class OpenCostComponent: Component<OpenCostVersion> {
    OpenCost
  }

  data class OpenCostVersion(val openCostVersions: Map<OpenCostComponent, String>): Versions<OpenCostVersion>(openCostVersions.mapKeys { it.key as Component<OpenCostVersion> }) {
    companion object: VersionsFetcher<OpenCostVersion> by OpenCostVersionFetcher {
      fun String.toOpenCostVersion(): OpenCostVersion = OpenCostVersion(
        mapOf(
          OpenCostComponent.OpenCost to this,
        )
      )
    }
  }

  enum class ArgoComponent: Component<ArgoVersion> {
    ArgoCD,
    ArgoRollouts,
    ArgoWorkflows,
    ArgoEvents
  }

  data class ArgoVersion(val argoVersions: Map<ArgoComponent, String>): Versions<ArgoVersion>(argoVersions.mapKeys { it.key as Component<ArgoVersion> }) {
    fun plus(other: ArgoVersion): ArgoVersion = ArgoVersion(
      argoVersions + other.argoVersions
    )

    companion object: VersionsFetcher<ArgoVersion> by ArgoVersionFetcher {
      fun String.toArgoCDVersion(): ArgoVersion = ArgoVersion(
        mapOf(
          ArgoComponent.ArgoCD to this,
        )
      )

      fun String.toArgoRolloutsVersion(): ArgoVersion = ArgoVersion(
        mapOf(
          ArgoComponent.ArgoRollouts to this,
        )
      )

      fun String.toArgoWorkflowsVersion(): ArgoVersion = ArgoVersion(
        mapOf(
          ArgoComponent.ArgoWorkflows to this,
        )
      )

      fun String.toArgoEventsVersion(): ArgoVersion = ArgoVersion(
        mapOf(
          ArgoComponent.ArgoEvents to this,
        )
      )
    }
  }

  enum class TektonComponent: Component<TektonVersion> {
    TektonChains,
    TektonCli,
    TektonDashboard,
    TektonPipeline,
    TektonTriggers,
    TektonResults
  }

  data class TektonVersion(val argocdVersions: Map<TektonComponent, String>): Versions<TektonVersion>(argocdVersions.mapKeys { it.key as Component<TektonVersion> }) {
    companion object: VersionsFetcher<TektonVersion> by TektonVersionFetcher {
      fun String.toTektonChainsVersion(): TektonVersion = TektonVersion(
        mapOf(
          TektonComponent.TektonChains to this,
        )
      )

      fun String.toTektonCliVersion(): TektonVersion = TektonVersion(
        mapOf(
          TektonComponent.TektonCli to this,
        )
      )

      fun String.toTektonDashboardVersion(): TektonVersion = TektonVersion(
        mapOf(
          TektonComponent.TektonDashboard to this,
        )
      )

      fun String.toTektonPipelineVersion(): TektonVersion = TektonVersion(
        mapOf(
          TektonComponent.TektonPipeline to this,
        )
      )

      fun String.toTektonTriggersVersion(): TektonVersion = TektonVersion(
        mapOf(
          TektonComponent.TektonTriggers to this,
        )
      )

      fun String.toTektonResultsVersion(): TektonVersion = TektonVersion(
        mapOf(
          TektonComponent.TektonResults to this,
        )
      )
    }
  }

  enum class DenoComponent: Component<DenoVersion> {
    Deno
  }

  data class DenoVersion(val denoVersions: Map<DenoComponent, String>): Versions<DenoVersion>(denoVersions.mapKeys { it.key as Component<DenoVersion> }) {
    companion object: VersionsFetcher<DenoVersion> by DenoVersionFetcher {
      fun String.toDenoVersion(): DenoVersion = DenoVersion(
        mapOf(
          DenoComponent.Deno to this,
        )
      )
    }
  }

  enum class DotNetComponent: Component<DotNetVersion> {
    DotNet
  }

  data class DotNetVersion(val dotNetVersions: Map<DotNetComponent, String>): Versions<DotNetVersion>(dotNetVersions.mapKeys { it.key as Component<DotNetVersion> }) {
    companion object: VersionsFetcher<DotNetVersion> by DotNetVersionFetcher {
      fun String.toDotNetVersion(): DotNetVersion = DotNetVersion(
        mapOf(
          DotNetComponent.DotNet to this,
        )
      )
    }
  }

  enum class GolangComponent: Component<GolangVersion> {
    Golang
  }

  data class GolangVersion(val golangVersions: Map<GolangComponent, String>): Versions<GolangVersion>(golangVersions.mapKeys { it.key as Component<GolangVersion> }) {
    companion object: VersionsFetcher<GolangVersion> by GolangVersionFetcher {
      fun String.toGolangVersion(): GolangVersion = GolangVersion(
        mapOf(
          GolangComponent.Golang to this,
        )
      )
    }
  }

  enum class JavaComponent: Component<JavaVersion> {
    OpenJDK,
    OpenJ9,
    GraalVM
  }

  data class JavaVersion(val javaVersions: Map<JavaComponent, String>): Versions<JavaVersion>(javaVersions.mapKeys { it.key as Component<JavaVersion> }) {
    fun plus(other: JavaVersion): JavaVersion = JavaVersion(
      javaVersions + other.javaVersions
    )

    companion object: VersionsFetcher<JavaVersion> by JavaVersionFetcher {
      fun String.toOpenJ9Version(): JavaVersion = JavaVersion(
        mapOf(
          JavaComponent.OpenJ9 to this,
        )
      )

      fun String.toOpenJDKVersion(): JavaVersion = JavaVersion(
        mapOf(
          JavaComponent.OpenJDK to this,
        )
      )

      fun String.toGraalVM(): JavaVersion = JavaVersion(
        mapOf(
          JavaComponent.GraalVM to this,
        )
      )
    }
  }

  enum class NodeJsComponent: Component<NodeJsVersion> {
    NodeJs
  }

  data class NodeJsVersion(val nodeJsVersions: Map<NodeJsComponent, String>): Versions<NodeJsVersion>(nodeJsVersions.mapKeys { it.key as Component<NodeJsVersion> }) {
    companion object: VersionsFetcher<NodeJsVersion> by NodeJsVersionFetcher {
      fun String.toNodeJsVersion(): NodeJsVersion = NodeJsVersion(
        mapOf(
          NodeJsComponent.NodeJs to this,
        )
      )
    }
  }

  enum class PythonComponent: Component<PythonVersion> {
    CPython,
    PyPy,
  }

  data class PythonVersion(val pythonVersions: Map<PythonComponent, String>): Versions<PythonVersion>(pythonVersions.mapKeys { it.key as Component<PythonVersion> }) {
    fun plus(other: PythonVersion): PythonVersion = PythonVersion(
      pythonVersions + other.pythonVersions
    )

    companion object: VersionsFetcher<PythonVersion> by PythonVersionFetcher {
      fun String.toPythonVersion(): PythonVersion = PythonVersion(
        mapOf(
          PythonComponent.CPython to this,
        )
      )

      fun String.toPyPyVersion(): PythonVersion = PythonVersion(
        mapOf(
          PythonComponent.PyPy to this,
        )
      )
    }
  }

  enum class RubyComponent: Component<RubyVersion> {
    Ruby,
    MRuby,
  }

  data class RubyVersion(val rubyVersions: Map<RubyComponent, String>): Versions<RubyVersion>(rubyVersions.mapKeys { it.key as Component<RubyVersion> }) {
    companion object: VersionsFetcher<RubyVersion> by RubyVersionFetcher {
      fun String.toRubyVersion(): RubyVersion = RubyVersion(
        mapOf(
          RubyComponent.Ruby to this,
        )
      )
    }
  }

  enum class RustComponent: Component<RustVersion> {
    Nightly,
    Stable,
  }

  data class RustVersion(val rustVersions: Map<RustComponent, String>): Versions<RustVersion>(rustVersions.mapKeys { it.key as Component<RustVersion> }) {
    fun plus(other: RustVersion): RustVersion = RustVersion(
      rustVersions + other.rustVersions
    )

    companion object: VersionsFetcher<RustVersion> by RustVersionFetcher {
      fun String.toRustVersion(): RustVersion = RustVersion(
        mapOf(
          RustComponent.Stable to this,
        )
      )

      fun String.toRustNightlyVersion(): RustVersion = RustVersion(
        mapOf(
          RustComponent.Nightly to this,
        )
      )
    }
  }

  enum class DataFusionBallistaComponent: Component<DataFusionBallistaVersion> {
    DataFusionBallista,
  }

  data class DataFusionBallistaVersion(val dataFusionBallistaVersions: Map<DataFusionBallistaComponent, String>): Versions<DataFusionBallistaVersion>(dataFusionBallistaVersions.mapKeys { it.key as Component<DataFusionBallistaVersion> }) {
    companion object: VersionsFetcher<DataFusionBallistaVersion> by DataFusionBallistaVersionFetcher {
      fun String.toDataFusionBallistaVersion(): DataFusionBallistaVersion = DataFusionBallistaVersion(
        mapOf(
          DataFusionBallistaComponent.DataFusionBallista to this,
        )
      )
    }
  }

  enum class FeastComponent: Component<FeastVersion> {
    Feast,
  }

  data class FeastVersion(val feastVersions: Map<FeastComponent, String>): Versions<FeastVersion>(feastVersions.mapKeys { it.key as Component<FeastVersion> }) {
    companion object: VersionsFetcher<FeastVersion> by FeastVersionFetcher {
      fun String.toFeastVersion(): FeastVersion = FeastVersion(
        mapOf(
          FeastComponent.Feast to this,
        )
      )
    }
  }

  enum class KubeFlinkComponent: Component<KubeFlinkVersion> {
    KubeFlink,
  }

  data class KubeFlinkVersion(val kubeFlinkVersions: Map<KubeFlinkComponent, String>): Versions<KubeFlinkVersion>(kubeFlinkVersions.mapKeys { it.key as Component<KubeFlinkVersion> }) {
    companion object: VersionsFetcher<KubeFlinkVersion> by KubeFlinkVersionFetcher {
      fun String.toKubeFlinkVersion(): KubeFlinkVersion = KubeFlinkVersion(
        mapOf(
          KubeFlinkComponent.KubeFlink to this,
        )
      )
    }
  }

  enum class KubeRayComponent: Component<KubeRayVersion> {
    KubeRay,
  }

  data class KubeRayVersion(val kubeRayVersions: Map<KubeRayComponent, String>): Versions<KubeRayVersion>(kubeRayVersions.mapKeys { it.key as Component<KubeRayVersion> }) {
    companion object: VersionsFetcher<KubeRayVersion> by KubeRayVersionFetcher {
      fun String.toKubeRayVersion(): KubeRayVersion = KubeRayVersion(
        mapOf(
          KubeRayComponent.KubeRay to this,
        )
      )
    }
  }

  enum class VolcanoComponent: Component<VolcanoVersion> {
    Volcano,
  }

  data class VolcanoVersion(val volcanoVersions: Map<VolcanoComponent, String>): Versions<VolcanoVersion>(volcanoVersions.mapKeys { it.key as Component<VolcanoVersion> }) {
    companion object: VersionsFetcher<VolcanoVersion> by VolcanoVersionFetcher {
      fun String.toVolcanoVersion(): VolcanoVersion = VolcanoVersion(
        mapOf(
          VolcanoComponent.Volcano to this,
        )
      )
    }
  }

  enum class CiliumComponent: Component<CiliumVersion> {
    Cilium,
  }

  data class CiliumVersion(val ciliumVersions: Map<CiliumComponent, String>): Versions<CiliumVersion>(ciliumVersions.mapKeys { it.key as Component<CiliumVersion> }) {
    companion object: VersionsFetcher<CiliumVersion> by CiliumVersionFetcher {
      fun String.toCiliumVersion(): CiliumVersion = CiliumVersion(
        mapOf(
          CiliumComponent.Cilium to this,
        )
      )
    }
  }

  enum class CorazaComponent: Component<CorazaVersion> {
    Coraza,
  }

  data class CorazaVersion(val corazaVersions: Map<CorazaComponent, String>): Versions<CorazaVersion>(corazaVersions.mapKeys { it.key as Component<CorazaVersion> }) {
    companion object: VersionsFetcher<CorazaVersion> by CorazaVersionFetcher {
      fun String.toCorazaVersion(): CorazaVersion = CorazaVersion(
        mapOf(
          CorazaComponent.Coraza to this,
        )
      )
    }
  }

  enum class ExternalDnsComponent: Component<ExternalDnsVersion> {
    ExternalDns,
  }

  data class ExternalDnsVersion(val externalDnsVersions: Map<ExternalDnsComponent, String>): Versions<ExternalDnsVersion>(externalDnsVersions.mapKeys { it.key as Component<ExternalDnsVersion> }) {
    companion object: VersionsFetcher<ExternalDnsVersion> by ExternalDnsVersionFetcher {
      fun String.toExternalDnsVersion(): ExternalDnsVersion = ExternalDnsVersion(
        mapOf(
          ExternalDnsComponent.ExternalDns to this,
        )
      )
    }
  }

  enum class AlloyComponent: Component<AlloyVersion> {
    Alloy,
  }

  data class AlloyVersion(val alloyVersions: Map<AlloyComponent, String>): Versions<AlloyVersion>(alloyVersions.mapKeys { it.key as Component<AlloyVersion> }) {
    companion object: VersionsFetcher<AlloyVersion> by AlloyVersionFetcher {
      fun String.toAlloyVersion(): AlloyVersion = AlloyVersion(
        mapOf(
          AlloyComponent.Alloy to this,
        )
      )
    }
  }

  enum class GrafanaComponent: Component<GrafanaVersion> {
    Grafana,
  }

  data class GrafanaVersion(val grafanaVersions: Map<GrafanaComponent, String>): Versions<GrafanaVersion>(grafanaVersions.mapKeys { it.key as Component<GrafanaVersion> }) {
    companion object: VersionsFetcher<GrafanaVersion> by GrafanaVersionFetcher {
      fun String.toGrafanaVersion(): GrafanaVersion = GrafanaVersion(
        mapOf(
          GrafanaComponent.Grafana to this,
        )
      )
    }
  }

  enum class LokiComponent: Component<LokiVersion> {
    Loki,
  }

  data class LokiVersion(val lokiVersions: Map<LokiComponent, String>): Versions<LokiVersion>(lokiVersions.mapKeys { it.key as Component<LokiVersion> }) {
    companion object: VersionsFetcher<LokiVersion> by LokiVersionFetcher {
      fun String.toLokiVersion(): LokiVersion = LokiVersion(
        mapOf(
          LokiComponent.Loki to this,
        )
      )
    }
  }

  enum class MimirComponent: Component<MimirVersion> {
    Mimir,
  }

  data class MimirVersion(val mimirVersions: Map<MimirComponent, String>): Versions<MimirVersion>(mimirVersions.mapKeys { it.key as Component<MimirVersion> }) {
    companion object: VersionsFetcher<MimirVersion> by MimirVersionFetcher {
      fun String.toMimirVersion(): MimirVersion = MimirVersion(
        mapOf(
          MimirComponent.Mimir to this,
        )
      )
    }
  }

  enum class PrometheusComponent: Component<PrometheusVersion> {
    Prometheus,
  }

  data class PrometheusVersion(val prometheusVersions: Map<PrometheusComponent, String>): Versions<PrometheusVersion>(prometheusVersions.mapKeys { it.key as Component<PrometheusVersion> }) {
    companion object: VersionsFetcher<PrometheusVersion> by PrometheusVersionFetcher {
      fun String.toPrometheusVersion(): PrometheusVersion = PrometheusVersion(
        mapOf(
          PrometheusComponent.Prometheus to this,
        )
      )
    }
  }

  enum class TempoComponent: Component<TempoVersion> {
    Tempo,
  }

  data class TempoVersion(val tempoVersions: Map<TempoComponent, String>): Versions<TempoVersion>(tempoVersions.mapKeys { it.key as Component<TempoVersion> }) {
    companion object: VersionsFetcher<TempoVersion> by TempoVersionFetcher {
      fun String.toTempoVersion(): TempoVersion = TempoVersion(
        mapOf(
          TempoComponent.Tempo to this,
        )
      )
    }
  }

  enum class CertManagerComponent: Component<CertManagerVersion> {
    CertManager,
  }

  data class CertManagerVersion(val certManagerVersions: Map<CertManagerComponent, String>): Versions<CertManagerVersion>(certManagerVersions.mapKeys { it.key as Component<CertManagerVersion> }) {
    companion object: VersionsFetcher<CertManagerVersion> by CertManagerVersionFetcher {
      fun String.toCertManagerVersion(): CertManagerVersion = CertManagerVersion(
        mapOf(
          CertManagerComponent.CertManager to this,
        )
      )
    }
  }

  enum class ExternalSecretsComponent: Component<ExternalSecretsVersion> {
    ExternalSecrets,
  }

  data class ExternalSecretsVersion(val externalSecretsVersions: Map<ExternalSecretsComponent, String>): Versions<ExternalSecretsVersion>(externalSecretsVersions.mapKeys { it.key as Component<ExternalSecretsVersion> }) {
    companion object: VersionsFetcher<ExternalSecretsVersion> by ExternalSecretsVersionFetcher {
      fun String.toExternalSecretsVersion(): ExternalSecretsVersion = ExternalSecretsVersion(
        mapOf(
          ExternalSecretsComponent.ExternalSecrets to this,
        )
      )
    }
  }

  enum class FalcoComponent: Component<FalcoVersion> {
    Falco,
  }

  data class FalcoVersion(val falcoVersions: Map<FalcoComponent, String>): Versions<FalcoVersion>(falcoVersions.mapKeys { it.key as Component<FalcoVersion> }) {
    companion object: VersionsFetcher<FalcoVersion> by FalcoVersionFetcher {
      fun String.toFalcoVersion(): FalcoVersion = FalcoVersion(
        mapOf(
          FalcoComponent.Falco to this,
        )
      )
    }
  }

  enum class KyvernoComponent: Component<KyvernoVersion> {
    Kyverno,
  }

  data class KyvernoVersion(val kyvernoVersions: Map<KyvernoComponent, String>): Versions<KyvernoVersion>(kyvernoVersions.mapKeys { it.key as Component<KyvernoVersion> }) {
    companion object: VersionsFetcher<KyvernoVersion> by KyvernoVersionFetcher {
      fun String.toKyvernoVersion(): KyvernoVersion = KyvernoVersion(
        mapOf(
          KyvernoComponent.Kyverno to this,
        )
      )
    }
  }

  enum class OpenBaoComponent: Component<OpenBaoVersion> {
    OpenBao,
  }

  data class OpenBaoVersion(val openBaoVersions: Map<OpenBaoComponent, String>): Versions<OpenBaoVersion>(openBaoVersions.mapKeys { it.key as Component<OpenBaoVersion> }) {
    companion object: VersionsFetcher<OpenBaoVersion> by OpenBaoVersionFetcher {
      fun String.toOpenBaoVersion(): OpenBaoVersion = OpenBaoVersion(
        mapOf(
          OpenBaoComponent.OpenBao to this,
        )
      )
    }
  }

  enum class LocalPathProvisionerComponent: Component<LocalPathProvisionerVersion> {
    LocalPathProvisioner,
  }

  data class LocalPathProvisionerVersion(val localPathProvisionerVersions: Map<LocalPathProvisionerComponent, String>): Versions<LocalPathProvisionerVersion>(localPathProvisionerVersions.mapKeys { it.key as Component<LocalPathProvisionerVersion> }) {
    companion object: VersionsFetcher<LocalPathProvisionerVersion> by LocalPathProvisionerVersionFetcher {
      fun String.toLocalPathProvisionerVersion(): LocalPathProvisionerVersion = LocalPathProvisionerVersion(
        mapOf(
          LocalPathProvisionerComponent.LocalPathProvisioner to this,
        )
      )
    }
  }

  enum class MinioComponent: Component<MinioVersion> {
    Minio,
  }

  data class MinioVersion(val minioVersions: Map<MinioComponent, String>): Versions<MinioVersion>(minioVersions.mapKeys { it.key as Component<MinioVersion> }) {
    companion object: VersionsFetcher<MinioVersion> by MinioVersionFetcher {
      fun String.toMinioVersion(): MinioVersion = MinioVersion(
        mapOf(
          MinioComponent.Minio to this,
        )
      )
    }
  }

  enum class PostgreSQLComponent: Component<PostgreSQLVersion> {
    PostgreSQL,
    CNPG,
    Stackgres
  }

  data class PostgreSQLVersion(val postgresqlVersions: Map<PostgreSQLComponent, String>): Versions<PostgreSQLVersion>(postgresqlVersions.mapKeys { it.key as Component<PostgreSQLVersion> }) {
    companion object: VersionsFetcher<PostgreSQLVersion> by PostgreSQLVersionFetcher {
      fun String.toPostgreSQLVersion(): PostgreSQLVersion = PostgreSQLVersion(
        mapOf(
          PostgreSQLComponent.PostgreSQL to this,
        )
      )
    }
  }

  enum class PVCAutoresizerComponent: Component<PVCAutoresizerVersion> {
    PVCAutoresizer,
  }

  data class PVCAutoresizerVersion(val pvcAutoresizerVersions: Map<PVCAutoresizerComponent, String>): Versions<PVCAutoresizerVersion>(pvcAutoresizerVersions.mapKeys { it.key as Component<PVCAutoresizerVersion> }) {
    companion object: VersionsFetcher<PVCAutoresizerVersion> by PVCAutoresizerVersionFetcher {
      fun String.toPVCAutoresizerVersion(): PVCAutoresizerVersion = PVCAutoresizerVersion(
        mapOf(
          PVCAutoresizerComponent.PVCAutoresizer to this,
        )
      )
    }
  }

  enum class RookComponent: Component<RookVersion> {
    Rook,
  }

  data class RookVersion(val rookVersions: Map<RookComponent, String>): Versions<RookVersion>(rookVersions.mapKeys { it.key as Component<RookVersion> }) {
    companion object: VersionsFetcher<RookVersion> by RookVersionFetcher {
      fun String.toRookVersion(): RookVersion = RookVersion(
        mapOf(
          RookComponent.Rook to this,
        )
      )
    }
  }

  enum class ScyllaDBComponent: Component<ScyllaDBVersion> {
    ScyllaDB,
  }

  data class ScyllaDBVersion(val scyllaDBVersions: Map<ScyllaDBComponent, String>): Versions<ScyllaDBVersion>(scyllaDBVersions.mapKeys { it.key as Component<ScyllaDBVersion> }) {
    companion object: VersionsFetcher<ScyllaDBVersion> by ScyllaDBVersionFetcher {
      fun String.toScyllaDBVersion(): ScyllaDBVersion = ScyllaDBVersion(
        mapOf(
          ScyllaDBComponent.ScyllaDB to this,
        )
      )
    }
  }

  enum class TopoLvmComponent: Component<TopoLvmVersion> {
    TopoLvm,
  }

  data class TopoLvmVersion(val topoLvmVersions: Map<TopoLvmComponent, String>): Versions<TopoLvmVersion>(topoLvmVersions.mapKeys { it.key as Component<TopoLvmVersion> }) {
    companion object: VersionsFetcher<TopoLvmVersion> by TopoLvmVersionFetcher {
      fun String.toTopoLvmVersion(): TopoLvmVersion = TopoLvmVersion(
        mapOf(
          TopoLvmComponent.TopoLvm to this,
        )
      )
    }
  }

  enum class VeleroComponent: Component<VeleroVersion> {
    Velero,
  }

  data class VeleroVersion(val veleroVersions: Map<VeleroComponent, String>): Versions<VeleroVersion>(veleroVersions.mapKeys { it.key as Component<VeleroVersion> }) {
    companion object: VersionsFetcher<VeleroVersion> by VeleroVersionFetcher {
      fun String.toVeleroVersion(): VeleroVersion = VeleroVersion(
        mapOf(
          VeleroComponent.Velero to this,
        )
      )
    }
  }

  // Cli Tools

  enum class AwsCliComponent: Component<AwsCliVersion> {
    AwsCli,
  }

  data class AwsCliVersion(val awsCliVersions: Map<AwsCliComponent, String>): Versions<AwsCliVersion>(awsCliVersions.mapKeys { it.key as Component<AwsCliVersion> }) {
    companion object: VersionsFetcher<AwsCliVersion> by AwsCliVersionFetcher {
      fun String.toAwsCliVersion(): AwsCliVersion = AwsCliVersion(
        mapOf(
          AwsCliComponent.AwsCli to this,
        )
      )
    }
  }

  enum class BazelComponent: Component<BazelVersion> {
    Bazel,
    Bazelisk
  }

  data class BazelVersion(val bazelVersions: Map<BazelComponent, String>): Versions<BazelVersion>(bazelVersions.mapKeys { it.key as Component<BazelVersion> }) {
    companion object: VersionsFetcher<BazelVersion> by BazelVersionFetcher {
      fun String.toBazelVersion(): BazelVersion = BazelVersion(
        mapOf(
          BazelComponent.Bazel to this,
        )
      )
    }
  }

  enum class CmakeComponent: Component<CmakeVersion> {
    Cmake,
  }

  data class CmakeVersion(val cmakeVersions: Map<CmakeComponent, String>): Versions<CmakeVersion>(cmakeVersions.mapKeys { it.key as Component<CmakeVersion> }) {
    companion object: VersionsFetcher<CmakeVersion> by CmakeVersionFetcher {
      fun String.toCmakeVersion(): CmakeVersion = CmakeVersion(
        mapOf(
          CmakeComponent.Cmake to this,
        )
      )
    }
  }

  enum class CosignComponent: Component<CosignVersion> {
    Cosign
  }

  data class CosignVersion(val cosignVersions: Map<CosignComponent, String>): Versions<CosignVersion>(cosignVersions.mapKeys { it.key as Component<CosignVersion> }) {
    companion object: VersionsFetcher<CosignVersion> by CosignVersionFetcher {
      fun String.toCosignVersion(): CosignVersion = CosignVersion(
        mapOf(
          CosignComponent.Cosign to this,
        )
      )
    }
  }

  enum class DoCtlComponent: Component<DoCtlVersion> {
    DoCtl
  }

  data class DoCtlVersion(val doCtlVersions: Map<DoCtlComponent, String>): Versions<DoCtlVersion>(doCtlVersions.mapKeys { it.key as Component<DoCtlVersion> }) {
    companion object: VersionsFetcher<DoCtlVersion> by DoCtlVersionFetcher {
      fun String.toDoCtlVersion(): DoCtlVersion = DoCtlVersion(
        mapOf(
          DoCtlComponent.DoCtl to this,
        )
      )
    }
  }

  enum class FirebaseComponent: Component<FirebaseVersion> {
    Firebase
  }

  data class FirebaseVersion(val firebaseVersions: Map<FirebaseComponent, String>): Versions<FirebaseVersion>(firebaseVersions.mapKeys { it.key as Component<FirebaseVersion> }) {
    companion object: VersionsFetcher<FirebaseVersion> by FirebaseVersionFetcher {
      fun String.toFirebaseVersion(): FirebaseVersion = FirebaseVersion(
        mapOf(
          FirebaseComponent.Firebase to this,
        )
      )
    }
  }

  enum class GCloudComponent: Component<GCloudVersion> {
    GCloud
  }

  data class GCloudVersion(val gcloudVersions: Map<GCloudComponent, String>): Versions<GCloudVersion>(gcloudVersions.mapKeys { it.key as Component<GCloudVersion> }) {
    companion object: VersionsFetcher<GCloudVersion> by GCloudVersionFetcher {
      fun String.toGCloudVersion(): GCloudVersion = GCloudVersion(
        mapOf(
          GCloudComponent.GCloud to this,
        )
      )
    }
  }

  enum class GradleComponent: Component<GradleVersion> {
    Gradle
  }

  data class GradleVersion(val gradleVersions: Map<GradleComponent, String>): Versions<GradleVersion>(gradleVersions.mapKeys { it.key as Component<GradleVersion> }) {
    companion object: VersionsFetcher<GradleVersion> by GradleVersionFetcher {
      fun String.toGradleVersion(): GradleVersion = GradleVersion(
        mapOf(
          GradleComponent.Gradle to this,
        )
      )
    }
  }

  enum class GrypeComponent: Component<GrypeVersion> {
    Grype
  }

  data class GrypeVersion(val grypeVersions: Map<GrypeComponent, String>): Versions<GrypeVersion>(grypeVersions.mapKeys { it.key as Component<GrypeVersion> }) {
    companion object: VersionsFetcher<GrypeVersion> by GrypeVersionFetcher {
      fun String.toGrypeVersion(): GrypeVersion = GrypeVersion(
        mapOf(
          GrypeComponent.Grype to this,
        )
      )
    }
  }

  enum class HelmComponent: Component<HelmVersion> {
    Helm
  }

  data class HelmVersion(val helmVersions: Map<HelmComponent, String>): Versions<HelmVersion>(helmVersions.mapKeys { it.key as Component<HelmVersion> }) {
    companion object: VersionsFetcher<HelmVersion> by HelmVersionFetcher {
      fun String.toHelmVersion(): HelmVersion = HelmVersion(
        mapOf(
          HelmComponent.Helm to this,
        )
      )
    }
  }

  enum class K9sComponent: Component<K9sVersion> {
    K9s
  }

  data class K9sVersion(val k9sVersions: Map<K9sComponent, String>): Versions<K9sVersion>(k9sVersions.mapKeys { it.key as Component<K9sVersion> }) {
    companion object: VersionsFetcher<K9sVersion> by K9sVersionFetcher {
      fun String.toK9sVersion(): K9sVersion = K9sVersion(
        mapOf(
          K9sComponent.K9s to this,
        )
      )
    }
  }

  enum class KindComponent: Component<KindVersion> {
    Kind
  }

  data class KindVersion(val kindVersions: Map<KindComponent, String>): Versions<KindVersion>(kindVersions.mapKeys { it.key as Component<KindVersion> }) {
    companion object: VersionsFetcher<KindVersion> by KindVersionFetcher {
      fun String.toKindVersion(): KindVersion = KindVersion(
        mapOf(
          KindComponent.Kind to this,
        )
      )
    }
  }

  enum class KubeCtlComponent: Component<KubeCtlVersion> {
    KubeCtl
  }

  data class KubeCtlVersion(val kubeCtlVersions: Map<KubeCtlComponent, String>): Versions<KubeCtlVersion>(kubeCtlVersions.mapKeys { it.key as Component<KubeCtlVersion> }) {
    companion object: VersionsFetcher<KubeCtlVersion> by KubeCtlVersionFetcher {
      fun String.toKubeCtlVersion(): KubeCtlVersion = KubeCtlVersion(
        mapOf(
          KubeCtlComponent.KubeCtl to this,
        )
      )
    }
  }

  enum class MinikubeComponent: Component<MinikubeVersion> {
    Minikube
  }

  data class MinikubeVersion(val minikubeVersions: Map<MinikubeComponent, String>): Versions<MinikubeVersion>(minikubeVersions.mapKeys { it.key as Component<MinikubeVersion> }) {
    companion object: VersionsFetcher<MinikubeVersion> by MinikubeVersionFetcher {
      fun String.toMinikubeVersion(): MinikubeVersion = MinikubeVersion(
        mapOf(
          MinikubeComponent.Minikube to this,
        )
      )
    }
  }

  enum class OpenTofuComponent: Component<OpenTofuVersion> {
    OpenTofu
  }

  data class OpenTofuVersion(val openTofuVersions: Map<OpenTofuComponent, String>): Versions<OpenTofuVersion>(openTofuVersions.mapKeys { it.key as Component<OpenTofuVersion> }) {
    companion object: VersionsFetcher<OpenTofuVersion> by OpenTofuVersionFetcher {
      fun String.toOpenTofuVersion(): OpenTofuVersion = OpenTofuVersion(
        mapOf(
          OpenTofuComponent.OpenTofu to this,
        )
      )
    }
  }

  enum class PipXComponent: Component<PipXVersion> {
    PipX
  }

  data class PipXVersion(val pipXVersions: Map<PipXComponent, String>): Versions<PipXVersion>(pipXVersions.mapKeys { it.key as Component<PipXVersion> }) {
    companion object: VersionsFetcher<PipXVersion> by PipXVersionFetcher {
      fun String.toPipXVersion(): PipXVersion = PipXVersion(
        mapOf(
          PipXComponent.PipX to this,
        )
      )
    }
  }

  enum class PodmanComponent: Component<PodmanVersion> {
    Podman
  }

  data class PodmanVersion(val podmanVersions: Map<PodmanComponent, String>): Versions<PodmanVersion>(podmanVersions.mapKeys { it.key as Component<PodmanVersion> }) {
    companion object: VersionsFetcher<PodmanVersion> by PodmanVersionFetcher {
      fun String.toPipXVersion(): PipXVersion = PipXVersion(
        mapOf(
          PipXComponent.PipX to this,
        )
      )
    }
  }

  enum class ProtocComponent: Component<ProtocVersion> {
    Protoc
  }

  data class ProtocVersion(val protocVersions: Map<ProtocComponent, String>): Versions<ProtocVersion>(protocVersions.mapKeys { it.key as Component<ProtocVersion> }) {
    companion object: VersionsFetcher<ProtocVersion> by ProtocVersionFetcher {
      fun String.toProtocVersion(): ProtocVersion = ProtocVersion(
        mapOf(
          ProtocComponent.Protoc to this,
        )
      )
    }
  }

  enum class ScalaComponent: Component<ScalaVersion> {
    Scala,
    Sbt
  }

  data class ScalaVersion(val scalaVersions: Map<ScalaComponent, String>): Versions<ScalaVersion>(scalaVersions.mapKeys { it.key as Component<ScalaVersion> }) {
    companion object: VersionsFetcher<ScalaVersion> by ScalaVersionFetcher {
      fun String.toScalaVersion(): ScalaVersion = ScalaVersion(
        mapOf(
          ScalaComponent.Scala to this,
        )
      )
    }
  }

  enum class SyftComponent: Component<SyftVersion> {
    Syft
  }

  data class SyftVersion(val syftVersions: Map<SyftComponent, String>): Versions<SyftVersion>(syftVersions.mapKeys { it.key as Component<SyftVersion> }) {
    companion object: VersionsFetcher<SyftVersion> by SyftVersionFetcher {
      fun String.toSyftVersion(): SyftVersion = SyftVersion(
        mapOf(
          SyftComponent.Syft to this,
        )
      )
    }
  }

  enum class TerraformComponent: Component<TerraformVersion> {
    Terraform
  }

  data class TerraformVersion(val terraformVersions: Map<TerraformComponent, String>): Versions<TerraformVersion>(terraformVersions.mapKeys { it.key as Component<TerraformVersion> }) {
    companion object: VersionsFetcher<TerraformVersion> by TerraformVersionFetcher {
      fun String.toTerraformVersion(): TerraformVersion = TerraformVersion(
        mapOf(
          TerraformComponent.Terraform to this,
        )
      )
    }
  }

  enum class TrivyComponent: Component<TrivyVersion> {
    Trivy
  }

  data class TrivyVersion(val trivyVersions: Map<TrivyComponent, String>): Versions<TrivyVersion>(trivyVersions.mapKeys { it.key as Component<TrivyVersion> }) {
    companion object: VersionsFetcher<TrivyVersion> by TrivyVersionFetcher {
      fun String.toTrivyVersion(): TrivyVersion = TrivyVersion(
        mapOf(
          TrivyComponent.Trivy to this,
        )
      )
    }
  }

  enum class UVComponent: Component<UVVersion> {
    UV
  }

  data class UVVersion(val uvVersions: Map<UVComponent, String>): Versions<UVVersion>(uvVersions.mapKeys { it.key as Component<UVVersion> }) {
    companion object: VersionsFetcher<UVVersion> by UVVersionFetcher {
      fun String.toUVVersion(): UVVersion = UVVersion(
        mapOf(
          UVComponent.UV to this,
        )
      )
    }
  }
}
