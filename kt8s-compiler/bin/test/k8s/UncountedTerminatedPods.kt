package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param failed failed holds UIDs of failed Pods.
 * @param succeeded succeeded holds UIDs of succeeded Pods.
 */
@Serializable
public data class UncountedTerminatedPods(
  public val failed: List<String>,
  public val succeeded: List<String>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.batch/v1"

  @Transient
  override val group: String = "io.k8s.api.batch"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "UncountedTerminatedPods"
}