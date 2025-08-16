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
 * @param status Specifies the required Pod condition status. To match a pod condition it is required that the specified status equals the pod condition status. Defaults to True.
 * @param type Specifies the required Pod condition type. To match a pod condition it is required that specified type equals the pod condition type.
 */
@Serializable
public data class PodFailurePolicyOnPodConditionsPattern(
  public val status: String,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.batch/v1"

  @Transient
  override val group: String = "io.k8s.api.batch"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "PodFailurePolicyOnPodConditionsPattern"
}