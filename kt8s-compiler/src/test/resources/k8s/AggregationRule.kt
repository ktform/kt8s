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
 * @param clusterRoleSelectors ClusterRoleSelectors holds a list of selectors which will be used to find ClusterRoles and create the rules. If any of the selectors match, then the ClusterRole's permissions will be added
 */
@Serializable
public data class AggregationRule(
  public val clusterRoleSelectors: List<LabelSelector>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.rbac/v1"

  @Transient
  override val group: String = "io.k8s.api.rbac"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "AggregationRule"
}