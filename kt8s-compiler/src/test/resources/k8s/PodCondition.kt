package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param lastProbeTime Last time we probed the condition.
 * @param lastTransitionTime Last time the condition transitioned from one status to another.
 * @param message Human-readable message indicating details about last transition.
 * @param observedGeneration If set, this represents the .metadata.generation that the pod condition was set based upon. This is an alpha field. Enable PodObservedGenerationTracking to be able to use this field.
 * @param reason Unique, one-word, CamelCase reason for the condition's last transition.
 * @param status Status is the status of the condition. Can be True, False, Unknown. More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#pod-conditions
 * @param type Type is the type of the condition. More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#pod-conditions
 */
@Serializable
public data class PodCondition(
  public val lastProbeTime: KubernetesTime,
  public val lastTransitionTime: KubernetesTime,
  public val message: String,
  public val observedGeneration: Long,
  public val reason: String,
  public val status: String,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "PodCondition"
}