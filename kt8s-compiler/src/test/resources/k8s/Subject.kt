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
 * @param serviceAccount `serviceAccount` matches ServiceAccounts.
 * @param user `user` matches based on username.
 */
@Serializable
public data class Subject(
  public val serviceAccount: ServiceAccountSubject,
  public val user: UserSubject,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

  @Transient
  override val group: String = "io.k8s.api.flowcontrol"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "Subject"
}