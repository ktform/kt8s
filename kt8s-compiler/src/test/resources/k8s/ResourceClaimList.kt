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
 * @param items Items is the list of resource claims.
 * @param metadata Standard list metadata
 */
@Serializable
public data class ResourceClaimList(
  public val items: List<ResourceClaim>,
  public val metadata: ListMeta,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "resource.k8s.io/v1alpha3"

  @Transient
  override val group: String = "resource.k8s.io"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "ResourceClaimList"
}