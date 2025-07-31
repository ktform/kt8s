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
 * @param forNodes forNodes indicates the node(s) this endpoint should be consumed by when using topology aware routing. May contain a maximum of 8 entries. This is an Alpha feature and is only used when the PreferSameTrafficDistribution feature gate is enabled.
 * @param forZones forZones indicates the zone(s) this endpoint should be consumed by when using topology aware routing. May contain a maximum of 8 entries.
 */
@Serializable
public data class EndpointHints(
  public val forNodes: List<ForNode>,
  public val forZones: List<ForZone>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.discovery/v1"

  @Transient
  override val group: String = "io.k8s.api.discovery"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "EndpointHints"
}