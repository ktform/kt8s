package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param audiences Audiences are the intendend audiences of the token. A recipient of a token must identify themself with an identifier in the list of audiences of the token, and otherwise should reject the token. A token issued for multiple audiences may be used to authenticate against any of the audiences listed but implies a high degree of trust between the target audiences.
 * @param boundObjectRef BoundObjectRef is a reference to an object that the token will be bound to. The token will only be valid for as long as the bound object exists. NOTE: The API server's TokenReview endpoint will validate the BoundObjectRef, but other audiences may not. Keep ExpirationSeconds small if you want prompt revocation.
 * @param expirationSeconds ExpirationSeconds is the requested duration of validity of the request. The token issuer may return a token with a different validity duration so a client needs to check the 'expiration' field in a response.
 */
@Serializable
public data class TokenRequestSpec(
  public val audiences: List<String>,
  public val boundObjectRef: BoundObjectReference,
  public val expirationSeconds: Long,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.authentication/v1"

  @Transient
  override val group: String = "io.k8s.api.authentication"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "TokenRequestSpec"
}