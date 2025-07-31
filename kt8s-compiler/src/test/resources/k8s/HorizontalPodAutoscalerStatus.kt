package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param currentCPUUtilizationPercentage currentCPUUtilizationPercentage is the current average CPU utilization over all pods, represented as a percentage of requested CPU, e.g. 70 means that an average pod is using now 70% of its requested CPU.
 * @param currentReplicas currentReplicas is the current number of replicas of pods managed by this autoscaler.
 * @param desiredReplicas desiredReplicas is the  desired number of replicas of pods managed by this autoscaler.
 * @param lastScaleTime lastScaleTime is the last time the HorizontalPodAutoscaler scaled the number of pods; used by the autoscaler to control how often the number of pods is changed.
 * @param observedGeneration observedGeneration is the most recent generation observed by this autoscaler.
 */
@Serializable
public data class HorizontalPodAutoscalerStatus(
  public val currentCPUUtilizationPercentage: Int,
  public val currentReplicas: Int,
  public val desiredReplicas: Int,
  public val lastScaleTime: KubernetesTime,
  public val observedGeneration: Long,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.autoscaling/v1"

  @Transient
  override val group: String = "io.k8s.api.autoscaling"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "HorizontalPodAutoscalerStatus"
}