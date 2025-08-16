package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param start start is the number representing the first replica's index. It may be used to number replicas from an alternate index (eg: 1-indexed) over the default 0-indexed names, or to orchestrate progressive movement of replicas from one StatefulSet to another. If set, replica indices will be in the range:
 *   [.spec.ordinals.start, .spec.ordinals.start + .spec.replicas).
 * If unset, defaults to 0. Replica indices will be in the range:
 *   [0, .spec.replicas).
 */
@Serializable
public data class StatefulSetOrdinals(
  public val start: Int,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.apps/v1"

  @Transient
  override val group: String = "io.k8s.api.apps"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "StatefulSetOrdinals"
}