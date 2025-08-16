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
 * @param metadata Standard object's metadata.
 * @param roleRef RoleRef can reference a Role in the current namespace or a ClusterRole in the global namespace. If the RoleRef cannot be resolved, the Authorizer must return an error. This field is immutable.
 * @param subjects Subjects holds references to the objects the role applies to.
 */
@Serializable
public data class RoleBinding(
  public val metadata: ObjectMeta,
  public val roleRef: RoleRef,
  public val subjects: List<Subject>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "rbac.authorization.k8s.io/v1"

  @Transient
  override val group: String = "rbac.authorization.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "RoleBinding"
}