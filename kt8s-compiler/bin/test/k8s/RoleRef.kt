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
 * @param apiGroup APIGroup is the group for the resource being referenced
 * @param name Name is the name of resource being referenced
 */
@Serializable
public data class RoleRef(
  public val apiGroup: String,
  public val name: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.rbac/v1"

  @Transient
  override val group: String = "io.k8s.api.rbac"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "RoleRef"
}