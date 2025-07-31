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
 * @param aggregationRule AggregationRule is an optional field that describes how to build the Rules for this ClusterRole. If AggregationRule is set, then the Rules are controller managed and direct changes to Rules will be stomped by the controller.
 * @param metadata Standard object's metadata.
 * @param rules Rules holds all the PolicyRules for this ClusterRole
 */
@Serializable
public data class ClusterRole(
  public val aggregationRule: AggregationRule,
  public val metadata: ObjectMeta,
  public val rules: List<PolicyRule>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "rbac.authorization.k8s.io/v1"

  @Transient
  override val group: String = "rbac.authorization.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ClusterRole"
}