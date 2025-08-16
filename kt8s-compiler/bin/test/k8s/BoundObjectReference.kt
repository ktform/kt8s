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
 * @param name Name of the referent.
 * @param uid UID of the referent.
 */
@Serializable
public data class BoundObjectReference(
  public val name: String,
  public val uid: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.authentication/v1"

  @Transient
  override val group: String = "io.k8s.api.authentication"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "BoundObjectReference"
}