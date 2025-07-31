package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param conditions conditions holds an array of metav1.Condition that describe the state of the ServiceCIDR. Current service state
 */
@Serializable
public data class ServiceCIDRStatus(
  public val conditions: List<Condition>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.networking/v1"

  @Transient
  override val group: String = "io.k8s.api.networking"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ServiceCIDRStatus"
}