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
 * @param items Items is the list of Deployments.
 * @param metadata Standard list metadata.
 */
@Serializable
public data class DeploymentList(
  public val items: List<Deployment>,
  public val metadata: ListMeta,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "apps/v1"

  @Transient
  override val group: String = "apps"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "DeploymentList"
}