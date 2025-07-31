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
 * @param metadata Standard object metadata; More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata.
 * @param spec spec defines the behavior of the scale. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status.
 * @param status status is the current status of the scale. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status. Read-only.
 */
@Serializable
public data class Scale(
  public val metadata: ObjectMeta,
  public val spec: ScaleSpec,
  public val status: ScaleStatus,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "autoscaling/v1"

  @Transient
  override val group: String = "autoscaling"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "Scale"
}