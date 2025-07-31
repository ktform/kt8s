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
 * @param replicas replicas is the actual number of observed instances of the scaled object.
 * @param selector selector is the label query over pods that should match the replicas count. This is same as the label selector but in the string format to avoid introspection by clients. The string will be in the same format as the query-param syntax. More info about label selectors: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/
 */
@Serializable
public data class ScaleStatus(
  public val replicas: Int,
  public val selector: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.autoscaling/v1"

  @Transient
  override val group: String = "io.k8s.api.autoscaling"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ScaleStatus"
}