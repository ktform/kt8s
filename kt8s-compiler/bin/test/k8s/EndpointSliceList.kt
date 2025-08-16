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
 * @param items items is the list of endpoint slices
 * @param metadata Standard list metadata.
 */
@Serializable
public data class EndpointSliceList(
  public val items: List<EndpointSlice>,
  public val metadata: ListMeta,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "discovery.k8s.io/v1"

  @Transient
  override val group: String = "discovery.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "EndpointSliceList"
}