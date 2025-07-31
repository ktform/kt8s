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
 * @param nodeAffinity Describes node affinity scheduling rules for the pod.
 * @param podAffinity Describes pod affinity scheduling rules (e.g. co-locate this pod in the same node, zone, etc. as some other pod(s)).
 * @param podAntiAffinity Describes pod anti-affinity scheduling rules (e.g. avoid putting this pod in the same node, zone, etc. as some other pod(s)).
 */
@Serializable
public data class Affinity(
  public val nodeAffinity: NodeAffinity,
  public val podAffinity: PodAffinity,
  public val podAntiAffinity: PodAntiAffinity,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "Affinity"
}