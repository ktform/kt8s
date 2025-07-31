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
 * @param gid GID is the primary gid initially attached to the first process in the container
 * @param supplementalGroups SupplementalGroups are the supplemental groups initially attached to the first process in the container
 * @param uid UID is the primary uid initially attached to the first process in the container
 */
@Serializable
public data class LinuxContainerUser(
  public val gid: Long,
  public val supplementalGroups: List<Long>,
  public val uid: Long,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "LinuxContainerUser"
}