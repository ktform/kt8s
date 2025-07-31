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
 * @param readOnly readOnly defaults to false (read/write). ReadOnly here will force the ReadOnly setting in VolumeMounts.
 * @param secretName secretName is the  name of secret that contains Azure Storage Account Name and Key
 * @param shareName shareName is the azure share Name
 */
@Serializable
public data class AzureFileVolumeSource(
  public val readOnly: Boolean,
  public val secretName: String,
  public val shareName: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "AzureFileVolumeSource"
}