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
 * @param spec Spec holds information about the request being evaluated
 * @param status Status is filled in by the server and indicates whether the request can be authenticated.
 */
@Serializable
public data class TokenReview(
  public val metadata: ObjectMeta,
  public val spec: TokenReviewSpec,
  public val status: TokenReviewStatus,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "authentication.k8s.io/v1"

  @Transient
  override val group: String = "authentication.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "TokenReview"
}