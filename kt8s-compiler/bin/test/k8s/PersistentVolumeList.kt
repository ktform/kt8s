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
 * @param items items is a list of persistent volumes. More info: https://kubernetes.io/docs/concepts/storage/persistent-volumes
 * @param metadata Standard list metadata. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds
 */
@Serializable
public data class PersistentVolumeList(
  public val items: List<PersistentVolume>,
  public val metadata: ListMeta,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "v1"

  @Transient
  override val group: String = ""

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "PersistentVolumeList"
}