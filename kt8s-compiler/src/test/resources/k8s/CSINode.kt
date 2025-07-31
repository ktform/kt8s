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
 * @param metadata Standard object's metadata. metadata.name must be the Kubernetes node name.
 * @param spec spec is the specification of CSINode
 */
@Serializable
public data class CSINode(
  public val metadata: ObjectMeta,
  public val spec: CSINodeSpec,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "storage.k8s.io/v1"

  @Transient
  override val group: String = "storage.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CSINode"
}