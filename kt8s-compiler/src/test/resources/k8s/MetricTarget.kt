package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param averageUtilization averageUtilization is the target value of the average of the resource metric across all relevant pods, represented as a percentage of the requested value of the resource for the pods. Currently only valid for Resource metric source type
 * @param averageValue averageValue is the target value of the average of the metric across all relevant pods (as a quantity)
 * @param type type represents whether the metric type is Utilization, Value, or AverageValue
 * @param value value is the target value of the metric (as a quantity).
 */
@Serializable
public data class MetricTarget(
  public val averageUtilization: Int,
  public val averageValue: StringOrNumber,
  public val type: String,
  public val `value`: StringOrNumber,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.autoscaling/v2"

  @Transient
  override val group: String = "io.k8s.api.autoscaling"

  @Transient
  override val version: String = "v2"

  @SerialName("kind")
  override val kind: String = "MetricTarget"
}