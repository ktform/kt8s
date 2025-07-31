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
 * @param extra Any additional information provided by the authenticator.
 * @param groups The names of groups this user is a part of.
 * @param uid A unique value that identifies this user across time. If this user is deleted and another user by the same name is added, they will have different UIDs.
 * @param username The name that uniquely identifies this user among all active users.
 */
@Serializable
public data class UserInfo(
  public val extra: RawJsonObject,
  public val groups: List<String>,
  public val uid: String,
  public val username: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.authentication/v1"

  @Transient
  override val group: String = "io.k8s.api.authentication"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "UserInfo"
}