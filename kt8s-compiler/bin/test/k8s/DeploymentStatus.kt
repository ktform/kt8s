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
 * @param availableReplicas Total number of available non-terminating pods (ready for at least minReadySeconds) targeted by this deployment.
 * @param collisionCount Count of hash collisions for the Deployment. The Deployment controller uses this field as a collision avoidance mechanism when it needs to create the name for the newest ReplicaSet.
 * @param conditions Represents the latest available observations of a deployment's current state.
 * @param observedGeneration The generation observed by the deployment controller.
 * @param readyReplicas Total number of non-terminating pods targeted by this Deployment with a Ready Condition.
 * @param replicas Total number of non-terminating pods targeted by this deployment (their labels match the selector).
 * @param terminatingReplicas Total number of terminating pods targeted by this deployment. Terminating pods have a non-null .metadata.deletionTimestamp and have not yet reached the Failed or Succeeded .status.phase.
 *
 * This is an alpha field. Enable DeploymentReplicaSetTerminatingReplicas to be able to use this field.
 * @param unavailableReplicas Total number of unavailable pods targeted by this deployment. This is the total number of pods that are still required for the deployment to have 100% available capacity. They may either be pods that are running but not yet available or pods that still have not been created.
 * @param updatedReplicas Total number of non-terminating pods targeted by this deployment that have the desired template spec.
 */
@Serializable
public data class DeploymentStatus(
  public val availableReplicas: Int,
  public val collisionCount: Int,
  public val conditions: List<DeploymentCondition>,
  public val observedGeneration: Long,
  public val readyReplicas: Int,
  public val replicas: Int,
  public val terminatingReplicas: Int,
  public val unavailableReplicas: Int,
  public val updatedReplicas: Int,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.apps/v1"

  @Transient
  override val group: String = "io.k8s.api.apps"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "DeploymentStatus"
}