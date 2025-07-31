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
 * @param value Value defines how much of a certain device capacity is available.
 */
@Serializable
public data class DeviceCapacity(
  public val `value`: StringOrNumber,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.resource/v1beta1"

  @Transient
  override val group: String = "io.k8s.api.resource"

  @Transient
  override val version: String = "v1beta1"

  @SerialName("kind")
  override val kind: String = "DeviceCapacity"
}