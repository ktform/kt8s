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
 * @param availableReplicas The number of available non-terminating pods (ready for at least minReadySeconds) for this replica set.
 * @param conditions Represents the latest available observations of a replica set's current state.
 * @param fullyLabeledReplicas The number of non-terminating pods that have labels matching the labels of the pod template of the replicaset.
 * @param observedGeneration ObservedGeneration reflects the generation of the most recently observed ReplicaSet.
 * @param readyReplicas The number of non-terminating pods targeted by this ReplicaSet with a Ready Condition.
 * @param replicas Replicas is the most recently observed number of non-terminating pods. More info: https://kubernetes.io/docs/concepts/workloads/controllers/replicaset
 * @param terminatingReplicas The number of terminating pods for this replica set. Terminating pods have a non-null .metadata.deletionTimestamp and have not yet reached the Failed or Succeeded .status.phase.
 *
 * This is an alpha field. Enable DeploymentReplicaSetTerminatingReplicas to be able to use this field.
 */
@Serializable
public data class ReplicaSetStatus(
  public val availableReplicas: Int,
  public val conditions: List<ReplicaSetCondition>,
  public val fullyLabeledReplicas: Int,
  public val observedGeneration: Long,
  public val readyReplicas: Int,
  public val replicas: Int,
  public val terminatingReplicas: Int,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.apps/v1"

  @Transient
  override val group: String = "io.k8s.api.apps"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ReplicaSetStatus"
}