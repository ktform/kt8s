package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param generation Generation tracks the change in a pool over time. Whenever a driver changes something about one or more of the resources in a pool, it must change the generation in all ResourceSlices which are part of that pool. Consumers of ResourceSlices should only consider resources from the pool with the highest generation number. The generation may be reset by drivers, which should be fine for consumers, assuming that all ResourceSlices in a pool are updated to match or deleted.
 *
 * Combined with ResourceSliceCount, this mechanism enables consumers to detect pools which are comprised of multiple ResourceSlices and are in an incomplete state.
 * @param name Name is used to identify the pool. For node-local devices, this is often the node name, but this is not required.
 *
 * It must not be longer than 253 characters and must consist of one or more DNS sub-domains separated by slashes. This field is immutable.
 * @param resourceSliceCount ResourceSliceCount is the total number of ResourceSlices in the pool at this generation number. Must be greater than zero.
 *
 * Consumers can use this to check whether they have seen all ResourceSlices belonging to the same pool.
 */
@Serializable
public data class ResourcePool(
  public val generation: Long,
  public val name: String,
  public val resourceSliceCount: Long,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

  @Transient
  override val group: String = "io.k8s.api.resource"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "ResourcePool"
}