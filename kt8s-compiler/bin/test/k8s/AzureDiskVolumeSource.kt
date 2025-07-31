package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param cachingMode cachingMode is the Host Caching mode: None, Read Only, Read Write.
 * @param diskName diskName is the Name of the data disk in the blob storage
 * @param diskURI diskURI is the URI of data disk in the blob storage
 * @param fsType fsType is Filesystem type to mount. Must be a filesystem type supported by the host operating system. Ex. "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if unspecified.
 * @param readOnly readOnly Defaults to false (read/write). ReadOnly here will force the ReadOnly setting in VolumeMounts.
 */
@Serializable
public data class AzureDiskVolumeSource(
  public val cachingMode: String,
  public val diskName: String,
  public val diskURI: String,
  public val fsType: String,
  public val readOnly: Boolean,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "AzureDiskVolumeSource"
}