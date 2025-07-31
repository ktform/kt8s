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
 * @param fsType fsType is the filesystem type to mount. Must be a filesystem type supported by the host operating system. Ex. "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if unspecified.
 * @param readOnly readOnly defaults to false (read/write). ReadOnly here will force the ReadOnly setting in VolumeMounts.
 * @param secretRef secretRef specifies the secret to use for obtaining the StorageOS API credentials.  If not specified, default values will be attempted.
 * @param volumeName volumeName is the human-readable name of the StorageOS volume.  Volume names are only unique within a namespace.
 * @param volumeNamespace volumeNamespace specifies the scope of the volume within StorageOS.  If no namespace is specified then the Pod's namespace will be used.  This allows the Kubernetes name scoping to be mirrored within StorageOS for tighter integration. Set VolumeName to any name to override the default behaviour. Set to "default" if you are not using namespaces within StorageOS. Namespaces that do not pre-exist within StorageOS will be created.
 */
@Serializable
public data class StorageOSVolumeSource(
  public val fsType: String,
  public val readOnly: Boolean,
  public val secretRef: LocalObjectReference,
  public val volumeName: String,
  public val volumeNamespace: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "StorageOSVolumeSource"
}