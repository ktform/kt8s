package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param metadata Standard object metadata
 * @param spec Spec defines what can be allocated and how to configure it.
 *
 * This is mutable. Consumers have to be prepared for classes changing at any time, either because they get updated or replaced. Claim allocations are done once based on whatever was set in classes at the time of allocation.
 *
 * Changing the spec automatically increments the metadata.generation number.
 */
@Serializable
public data class DeviceClass(
  public val metadata: ObjectMeta,
  public val spec: DeviceClassSpec,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "resource.k8s.io/v1alpha3"

  @Transient
  override val group: String = "resource.k8s.io"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "DeviceClass"
}