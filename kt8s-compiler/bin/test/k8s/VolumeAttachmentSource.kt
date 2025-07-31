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
 * @param inlineVolumeSpec inlineVolumeSpec contains all the information necessary to attach a persistent volume defined by a pod's inline VolumeSource. This field is populated only for the CSIMigration feature. It contains translated fields from a pod's inline VolumeSource to a PersistentVolumeSpec. This field is beta-level and is only honored by servers that enabled the CSIMigration feature.
 * @param persistentVolumeName persistentVolumeName represents the name of the persistent volume to attach.
 */
@Serializable
public data class VolumeAttachmentSource(
  public val inlineVolumeSpec: PersistentVolumeSpec,
  public val persistentVolumeName: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.storage/v1"

  @Transient
  override val group: String = "io.k8s.api.storage"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "VolumeAttachmentSource"
}