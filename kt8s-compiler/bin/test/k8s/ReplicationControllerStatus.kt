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
 * @param availableReplicas The number of available replicas (ready for at least minReadySeconds) for this replication controller.
 * @param conditions Represents the latest available observations of a replication controller's current state.
 * @param fullyLabeledReplicas The number of pods that have labels matching the labels of the pod template of the replication controller.
 * @param observedGeneration ObservedGeneration reflects the generation of the most recently observed replication controller.
 * @param readyReplicas The number of ready replicas for this replication controller.
 * @param replicas Replicas is the most recently observed number of replicas. More info: https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller#what-is-a-replicationcontroller
 */
@Serializable
public data class ReplicationControllerStatus(
  public val availableReplicas: Int,
  public val conditions: List<ReplicationControllerCondition>,
  public val fullyLabeledReplicas: Int,
  public val observedGeneration: Long,
  public val readyReplicas: Int,
  public val replicas: Int,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ReplicationControllerStatus"
}