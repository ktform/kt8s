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
 * @param data Data is the serialized representation of the state.
 * @param metadata Standard object's metadata. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param revision Revision indicates the revision of the state represented by Data.
 */
@Serializable
public data class ControllerRevision(
  public val `data`: RawJsonObject,
  public val metadata: ObjectMeta,
  public val revision: Long,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "apps/v1"

  @Transient
  override val group: String = "apps"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ControllerRevision"
}