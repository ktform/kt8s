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
 * @param mountPath MountPath corresponds to the original VolumeMount.
 * @param name Name corresponds to the name of the original VolumeMount.
 * @param readOnly ReadOnly corresponds to the original VolumeMount.
 * @param recursiveReadOnly RecursiveReadOnly must be set to Disabled, Enabled, or unspecified (for non-readonly mounts). An IfPossible value in the original VolumeMount must be translated to Disabled or Enabled, depending on the mount result.
 */
@Serializable
public data class VolumeMountStatus(
  public val mountPath: String,
  public val name: String,
  public val readOnly: Boolean,
  public val recursiveReadOnly: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "VolumeMountStatus"
}