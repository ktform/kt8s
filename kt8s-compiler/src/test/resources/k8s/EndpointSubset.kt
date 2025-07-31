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
 * @param addresses IP addresses which offer the related ports that are marked as ready. These endpoints should be considered safe for load balancers and clients to utilize.
 * @param notReadyAddresses IP addresses which offer the related ports but are not currently marked as ready because they have not yet finished starting, have recently failed a readiness check, or have recently failed a liveness check.
 * @param ports Port numbers available on the related IP addresses.
 */
@Serializable
public data class EndpointSubset(
  public val addresses: List<EndpointAddress>,
  public val notReadyAddresses: List<EndpointAddress>,
  public val ports: List<EndpointPort>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "EndpointSubset"
}