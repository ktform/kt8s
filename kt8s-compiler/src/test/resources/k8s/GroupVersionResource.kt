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
 * @param resource The name of the resource.
 */
@Serializable
public data class GroupVersionResource(
  public val resource: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.storagemigration/v1alpha1"

  @Transient
  override val group: String = "io.k8s.api.storagemigration"

  @Transient
  override val version: String = "v1alpha1"

  @SerialName("kind")
  override val kind: String = "GroupVersionResource"
}