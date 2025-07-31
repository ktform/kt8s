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
 * @param attacher attacher indicates the name of the volume driver that MUST handle this request. This is the name returned by GetPluginName().
 * @param nodeName nodeName represents the node that the volume should be attached to.
 * @param source source represents the volume that should be attached.
 */
@Serializable
public data class VolumeAttachmentSpec(
  public val attacher: String,
  public val nodeName: String,
  public val source: VolumeAttachmentSource,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.storage/v1"

  @Transient
  override val group: String = "io.k8s.api.storage"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "VolumeAttachmentSpec"
}