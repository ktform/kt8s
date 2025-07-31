package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param allNodes AllNodes indicates that all nodes have access to the resources in the pool.
 *
 * Exactly one of NodeName, NodeSelector, AllNodes, and PerDeviceNodeSelection must be set.
 * @param devices Devices lists some or all of the devices in this pool.
 *
 * Must not have more than 128 entries.
 * @param driver Driver identifies the DRA driver providing the capacity information. A field selector can be used to list only ResourceSlice objects with a certain driver name.
 *
 * Must be a DNS subdomain and should end with a DNS domain owned by the vendor of the driver. This field is immutable.
 * @param nodeName NodeName identifies the node which provides the resources in this pool. A field selector can be used to list only ResourceSlice objects belonging to a certain node.
 *
 * This field can be used to limit access from nodes to ResourceSlices with the same node name. It also indicates to autoscalers that adding new nodes of the same type as some old node might also make new resources available.
 *
 * Exactly one of NodeName, NodeSelector, AllNodes, and PerDeviceNodeSelection must be set. This field is immutable.
 * @param nodeSelector NodeSelector defines which nodes have access to the resources in the pool, when that pool is not limited to a single node.
 *
 * Must use exactly one term.
 *
 * Exactly one of NodeName, NodeSelector, AllNodes, and PerDeviceNodeSelection must be set.
 * @param perDeviceNodeSelection PerDeviceNodeSelection defines whether the access from nodes to resources in the pool is set on the ResourceSlice level or on each device. If it is set to true, every device defined the ResourceSlice must specify this individually.
 *
 * Exactly one of NodeName, NodeSelector, AllNodes, and PerDeviceNodeSelection must be set.
 * @param pool Pool describes the pool that this ResourceSlice belongs to.
 * @param sharedCounters SharedCounters defines a list of counter sets, each of which has a name and a list of counters available.
 *
 * The names of the SharedCounters must be unique in the ResourceSlice.
 *
 * The maximum number of SharedCounters is 32.
 */
@Serializable
public data class ResourceSliceSpec(
  public val allNodes: Boolean,
  public val devices: List<Device>,
  public val driver: String,
  public val nodeName: String,
  public val nodeSelector: NodeSelector,
  public val perDeviceNodeSelection: Boolean,
  public val pool: ResourcePool,
  public val sharedCounters: List<CounterSet>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

  @Transient
  override val group: String = "io.k8s.api.resource"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "ResourceSliceSpec"
}