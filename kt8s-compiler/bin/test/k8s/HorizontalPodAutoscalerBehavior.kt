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
 * @param scaleDown scaleDown is scaling policy for scaling Down. If not set, the default value is to allow to scale down to minReplicas pods, with a 300 second stabilization window (i.e., the highest recommendation for the last 300sec is used).
 * @param scaleUp scaleUp is scaling policy for scaling Up. If not set, the default value is the higher of:
 *   * increase no more than 4 pods per 60 seconds
 *   * double the number of pods per 60 seconds
 * No stabilization is used.
 */
@Serializable
public data class HorizontalPodAutoscalerBehavior(
  public val scaleDown: HPAScalingRules,
  public val scaleUp: HPAScalingRules,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.autoscaling/v2"

  @Transient
  override val group: String = "io.k8s.api.autoscaling"

  @Transient
  override val version: String = "v2"

  @SerialName("kind")
  override val kind: String = "HorizontalPodAutoscalerBehavior"
}