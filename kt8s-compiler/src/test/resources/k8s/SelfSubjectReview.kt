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
 * @param metadata Standard object's metadata. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param status Status is filled in by the server with the user attributes.
 */
@Serializable
public data class SelfSubjectReview(
  public val metadata: ObjectMeta,
  public val status: SelfSubjectReviewStatus,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "authentication.k8s.io/v1"

  @Transient
  override val group: String = "authentication.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "SelfSubjectReview"
}