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
 * @param metadata Standard list metadata. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec Spec holds information about the request being evaluated
 * @param status Status is filled in by the server and indicates whether the request is allowed or not
 */
@Serializable
public data class SubjectAccessReview(
  public val metadata: ObjectMeta,
  public val spec: SubjectAccessReviewSpec,
  public val status: SubjectAccessReviewStatus,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "authorization.k8s.io/v1"

  @Transient
  override val group: String = "authorization.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "SubjectAccessReview"
}