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
 * @param default Default resource requirement limit value by resource name if resource limit is omitted.
 * @param defaultRequest DefaultRequest is the default resource requirement request value by resource name if resource request is omitted.
 * @param max Max usage constraints on this kind by resource name.
 * @param maxLimitRequestRatio MaxLimitRequestRatio if specified, the named resource must have a request and limit that are both non-zero where limit divided by request is less than or equal to the enumerated value; this represents the max burst for the named resource.
 * @param min Min usage constraints on this kind by resource name.
 * @param type Type of resource that this limit applies to.
 */
@Serializable
public data class LimitRangeItem(
  public val default: StringOrNumber,
  public val defaultRequest: StringOrNumber,
  public val max: StringOrNumber,
  public val maxLimitRequestRatio: StringOrNumber,
  public val min: StringOrNumber,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "LimitRangeItem"
}