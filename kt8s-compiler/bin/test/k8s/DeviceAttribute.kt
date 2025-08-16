package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param bool BoolValue is a true/false value.
 * @param int IntValue is a number.
 * @param string StringValue is a string. Must not be longer than 64 characters.
 */
@Serializable
public data class DeviceAttribute(
  public val bool: Boolean,
  public val int: Long,
  public val string: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

  @Transient
  override val group: String = "io.k8s.api.resource"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "DeviceAttribute"
}