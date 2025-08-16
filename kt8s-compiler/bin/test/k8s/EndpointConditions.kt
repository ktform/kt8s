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
 * @param ready ready indicates that this endpoint is ready to receive traffic, according to whatever system is managing the endpoint. A nil value should be interpreted as "true". In general, an endpoint should be marked ready if it is serving and not terminating, though this can be overridden in some cases, such as when the associated Service has set the publishNotReadyAddresses flag.
 * @param serving serving indicates that this endpoint is able to receive traffic, according to whatever system is managing the endpoint. For endpoints backed by pods, the EndpointSlice controller will mark the endpoint as serving if the pod's Ready condition is True. A nil value should be interpreted as "true".
 * @param terminating terminating indicates that this endpoint is terminating. A nil value should be interpreted as "false".
 */
@Serializable
public data class EndpointConditions(
  public val ready: Boolean,
  public val serving: Boolean,
  public val terminating: Boolean,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.discovery/v1"

  @Transient
  override val group: String = "io.k8s.api.discovery"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "EndpointConditions"
}