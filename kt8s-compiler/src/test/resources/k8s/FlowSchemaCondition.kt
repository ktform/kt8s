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
 * @param lastTransitionTime `lastTransitionTime` is the last time the condition transitioned from one status to another.
 * @param message `message` is a human-readable message indicating details about last transition.
 * @param reason `reason` is a unique, one-word, CamelCase reason for the condition's last transition.
 * @param status `status` is the status of the condition. Can be True, False, Unknown. Required.
 * @param type `type` is the type of the condition. Required.
 */
@Serializable
public data class FlowSchemaCondition(
  public val lastTransitionTime: KubernetesTime,
  public val message: String,
  public val reason: String,
  public val status: String,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

  @Transient
  override val group: String = "io.k8s.api.flowcontrol"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "FlowSchemaCondition"
}