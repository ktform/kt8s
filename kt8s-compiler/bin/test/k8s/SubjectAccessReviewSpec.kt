package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param extra Extra corresponds to the user.Info.GetExtra() method from the authenticator.  Since that is input to the authorizer it needs a reflection here.
 * @param groups Groups is the groups you're testing for.
 * @param nonResourceAttributes NonResourceAttributes describes information for a non-resource access request
 * @param resourceAttributes ResourceAuthorizationAttributes describes information for a resource access request
 * @param uid UID information about the requesting user.
 * @param user User is the user you're testing for. If you specify "User" but not "Groups", then is it interpreted as "What if User were not a member of any groups
 */
@Serializable
public data class SubjectAccessReviewSpec(
  public val extra: RawJsonObject,
  public val groups: List<String>,
  public val nonResourceAttributes: NonResourceAttributes,
  public val resourceAttributes: ResourceAttributes,
  public val uid: String,
  public val user: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.authorization/v1"

  @Transient
  override val group: String = "io.k8s.api.authorization"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "SubjectAccessReviewSpec"
}