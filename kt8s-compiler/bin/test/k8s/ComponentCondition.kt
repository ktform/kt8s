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
 * @param error Condition error code for a component. For example, a health check error code.
 * @param message Message about the condition for a component. For example, information about a health check.
 * @param status Status of the condition for a component. Valid values for "Healthy": "True", "False", or "Unknown".
 * @param type Type of condition for a component. Valid value: "Healthy"
 */
@Serializable
public data class ComponentCondition(
  public val error: String,
  public val message: String,
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
  override val kind: String = "ComponentCondition"
}