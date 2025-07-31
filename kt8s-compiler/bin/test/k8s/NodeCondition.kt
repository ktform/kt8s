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
 * @param lastHeartbeatTime Last time we got an update on a given condition.
 * @param lastTransitionTime Last time the condition transit from one status to another.
 * @param message Human readable message indicating details about last transition.
 * @param reason (brief) reason for the condition's last transition.
 * @param status Status of the condition, one of True, False, Unknown.
 * @param type Type of node condition.
 */
@Serializable
public data class NodeCondition(
  public val lastHeartbeatTime: KubernetesTime,
  public val lastTransitionTime: KubernetesTime,
  public val message: String,
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
  override val kind: String = "NodeCondition"
}