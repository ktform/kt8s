package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param audiences Audiences are audience identifiers chosen by the authenticator that are compatible with both the TokenReview and token. An identifier is any identifier in the intersection of the TokenReviewSpec audiences and the token's audiences. A client of the TokenReview API that sets the spec.audiences field should validate that a compatible audience identifier is returned in the status.audiences field to ensure that the TokenReview server is audience aware. If a TokenReview returns an empty status.audience field where status.authenticated is "true", the token is valid against the audience of the Kubernetes API server.
 * @param authenticated Authenticated indicates that the token was associated with a known user.
 * @param error Error indicates that the token couldn't be checked
 * @param user User is the UserInfo associated with the provided token.
 */
@Serializable
public data class TokenReviewStatus(
  public val audiences: List<String>,
  public val authenticated: Boolean,
  public val error: String,
  public val user: UserInfo,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.authentication/v1"

  @Transient
  override val group: String = "io.k8s.api.authentication"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "TokenReviewStatus"
}