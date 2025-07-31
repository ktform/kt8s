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
 * @param current current contains the current value for the given metric
 * @param describedObject DescribedObject specifies the descriptions of a object,such as kind,name apiVersion
 * @param metric metric identifies the target metric by name and selector
 */
@Serializable
public data class ObjectMetricStatus(
  public val current: MetricValueStatus,
  public val describedObject: CrossVersionObjectReference,
  public val metric: MetricIdentifier,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.autoscaling/v2"

  @Transient
  override val group: String = "io.k8s.api.autoscaling"

  @Transient
  override val version: String = "v2"

  @SerialName("kind")
  override val kind: String = "ObjectMetricStatus"
}