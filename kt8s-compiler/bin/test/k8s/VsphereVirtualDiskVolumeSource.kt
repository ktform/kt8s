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
 * @param fsType fsType is filesystem type to mount. Must be a filesystem type supported by the host operating system. Ex. "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if unspecified.
 * @param storagePolicyID storagePolicyID is the storage Policy Based Management (SPBM) profile ID associated with the StoragePolicyName.
 * @param storagePolicyName storagePolicyName is the storage Policy Based Management (SPBM) profile name.
 * @param volumePath volumePath is the path that identifies vSphere volume vmdk
 */
@Serializable
public data class VsphereVirtualDiskVolumeSource(
  public val fsType: String,
  public val storagePolicyID: String,
  public val storagePolicyName: String,
  public val volumePath: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "VsphereVirtualDiskVolumeSource"
}