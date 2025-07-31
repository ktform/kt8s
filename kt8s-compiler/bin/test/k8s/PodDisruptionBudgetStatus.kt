package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param conditions Conditions contain conditions for PDB. The disruption controller sets the DisruptionAllowed condition. The following are known values for the reason field (additional reasons could be added in the future): - SyncFailed: The controller encountered an error and wasn't able to compute
 *               the number of allowed disruptions. Therefore no disruptions are
 *               allowed and the status of the condition will be False.
 * - InsufficientPods: The number of pods are either at or below the number
 *                     required by the PodDisruptionBudget. No disruptions are
 *                     allowed and the status of the condition will be False.
 * - SufficientPods: There are more pods than required by the PodDisruptionBudget.
 *                   The condition will be True, and the number of allowed
 *                   disruptions are provided by the disruptionsAllowed property.
 * @param currentHealthy current number of healthy pods
 * @param desiredHealthy minimum desired number of healthy pods
 * @param disruptedPods DisruptedPods contains information about pods whose eviction was processed by the API server eviction subresource handler but has not yet been observed by the PodDisruptionBudget controller. A pod will be in this map from the time when the API server processed the eviction request to the time when the pod is seen by PDB controller as having been marked for deletion (or after a timeout). The key in the map is the name of the pod and the value is the time when the API server processed the eviction request. If the deletion didn't occur and a pod is still there it will be removed from the list automatically by PodDisruptionBudget controller after some time. If everything goes smooth this map should be empty for the most of the time. Large number of entries in the map may indicate problems with pod deletions.
 * @param disruptionsAllowed Number of pod disruptions that are currently allowed.
 * @param expectedPods total number of pods counted by this disruption budget
 * @param observedGeneration Most recent generation observed when updating this PDB status. DisruptionsAllowed and other status information is valid only if observedGeneration equals to PDB's object generation.
 */
@Serializable
public data class PodDisruptionBudgetStatus(
  public val conditions: List<Condition>,
  public val currentHealthy: Int,
  public val desiredHealthy: Int,
  public val disruptedPods: KubernetesTime,
  public val disruptionsAllowed: Int,
  public val expectedPods: Int,
  public val observedGeneration: Long,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.policy/v1"

  @Transient
  override val group: String = "io.k8s.api.policy"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "PodDisruptionBudgetStatus"
}