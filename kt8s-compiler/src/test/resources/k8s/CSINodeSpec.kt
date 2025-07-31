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
 * @param drivers drivers is a list of information of all CSI Drivers existing on a node. If all drivers in the list are uninstalled, this can become empty.
 */
@Serializable
public data class CSINodeSpec(
  public val drivers: List<CSINodeDriver>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.storage/v1"

  @Transient
  override val group: String = "io.k8s.api.storage"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CSINodeSpec"
}