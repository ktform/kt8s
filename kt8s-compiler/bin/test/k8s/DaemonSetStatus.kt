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
 * @param collisionCount Count of hash collisions for the DaemonSet. The DaemonSet controller uses this field as a collision avoidance mechanism when it needs to create the name for the newest ControllerRevision.
 * @param conditions Represents the latest available observations of a DaemonSet's current state.
 * @param currentNumberScheduled The number of nodes that are running at least 1 daemon pod and are supposed to run the daemon pod. More info: https://kubernetes.io/docs/concepts/workloads/controllers/daemonset/
 * @param desiredNumberScheduled The total number of nodes that should be running the daemon pod (including nodes correctly running the daemon pod). More info: https://kubernetes.io/docs/concepts/workloads/controllers/daemonset/
 * @param numberAvailable The number of nodes that should be running the daemon pod and have one or more of the daemon pod running and available (ready for at least spec.minReadySeconds)
 * @param numberMisscheduled The number of nodes that are running the daemon pod, but are not supposed to run the daemon pod. More info: https://kubernetes.io/docs/concepts/workloads/controllers/daemonset/
 * @param numberReady numberReady is the number of nodes that should be running the daemon pod and have one or more of the daemon pod running with a Ready Condition.
 * @param numberUnavailable The number of nodes that should be running the daemon pod and have none of the daemon pod running and available (ready for at least spec.minReadySeconds)
 * @param observedGeneration The most recent generation observed by the daemon set controller.
 * @param updatedNumberScheduled The total number of nodes that are running updated daemon pod
 */
@Serializable
public data class DaemonSetStatus(
  public val collisionCount: Int,
  public val conditions: List<DaemonSetCondition>,
  public val currentNumberScheduled: Int,
  public val desiredNumberScheduled: Int,
  public val numberAvailable: Int,
  public val numberMisscheduled: Int,
  public val numberReady: Int,
  public val numberUnavailable: Int,
  public val observedGeneration: Long,
  public val updatedNumberScheduled: Int,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.apps/v1"

  @Transient
  override val group: String = "io.k8s.api.apps"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "DaemonSetStatus"
}