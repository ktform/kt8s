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
 * @param deleteOptions DeleteOptions may be provided
 * @param metadata ObjectMeta describes the pod that is being evicted.
 */
@Serializable
public data class Eviction(
  public val deleteOptions: DeleteOptions,
  public val metadata: ObjectMeta,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "policy/v1"

  @Transient
  override val group: String = "policy"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "Eviction"
}