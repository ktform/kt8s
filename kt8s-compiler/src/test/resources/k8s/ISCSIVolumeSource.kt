package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param chapAuthDiscovery chapAuthDiscovery defines whether support iSCSI Discovery CHAP authentication
 * @param chapAuthSession chapAuthSession defines whether support iSCSI Session CHAP authentication
 * @param fsType fsType is the filesystem type of the volume that you want to mount. Tip: Ensure that the filesystem type is supported by the host operating system. Examples: "ext4", "xfs", "ntfs". Implicitly inferred to be "ext4" if unspecified. More info: https://kubernetes.io/docs/concepts/storage/volumes#iscsi
 * @param initiatorName initiatorName is the custom iSCSI Initiator Name. If initiatorName is specified with iscsiInterface simultaneously, new iSCSI interface <target portal>:<volume name> will be created for the connection.
 * @param iqn iqn is the target iSCSI Qualified Name.
 * @param iscsiInterface iscsiInterface is the interface Name that uses an iSCSI transport. Defaults to 'default' (tcp).
 * @param lun lun represents iSCSI Target Lun number.
 * @param portals portals is the iSCSI Target Portal List. The portal is either an IP or ip_addr:port if the port is other than default (typically TCP ports 860 and 3260).
 * @param readOnly readOnly here will force the ReadOnly setting in VolumeMounts. Defaults to false.
 * @param secretRef secretRef is the CHAP Secret for iSCSI target and initiator authentication
 * @param targetPortal targetPortal is iSCSI Target Portal. The Portal is either an IP or ip_addr:port if the port is other than default (typically TCP ports 860 and 3260).
 */
@Serializable
public data class ISCSIVolumeSource(
  public val chapAuthDiscovery: Boolean,
  public val chapAuthSession: Boolean,
  public val fsType: String,
  public val initiatorName: String,
  public val iqn: String,
  public val iscsiInterface: String,
  public val lun: Int,
  public val portals: List<String>,
  public val readOnly: Boolean,
  public val secretRef: LocalObjectReference,
  public val targetPortal: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ISCSIVolumeSource"
}