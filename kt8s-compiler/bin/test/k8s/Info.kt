package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param emulationMajor EmulationMajor is the major version of the emulation version
 * @param emulationMinor EmulationMinor is the minor version of the emulation version
 * @param major Major is the major version of the binary version
 * @param minCompatibilityMajor MinCompatibilityMajor is the major version of the minimum compatibility version
 * @param minCompatibilityMinor MinCompatibilityMinor is the minor version of the minimum compatibility version
 * @param minor Minor is the minor version of the binary version
 */
@Serializable
public data class Info(
  public val buildDate: String,
  public val compiler: String,
  public val emulationMajor: String,
  public val emulationMinor: String,
  public val gitCommit: String,
  public val gitTreeState: String,
  public val gitVersion: String,
  public val goVersion: String,
  public val major: String,
  public val minCompatibilityMajor: String,
  public val minCompatibilityMinor: String,
  public val minor: String,
  public val platform: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.apimachinery.pkg/version"

  @Transient
  override val group: String = "io.k8s.apimachinery.pkg"

  @Transient
  override val version: String = "version"

  @SerialName("kind")
  override val kind: String = "Info"
}