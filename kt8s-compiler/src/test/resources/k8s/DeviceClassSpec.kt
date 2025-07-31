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
 * @param config Config defines configuration parameters that apply to each device that is claimed via this class. Some classses may potentially be satisfied by multiple drivers, so each instance of a vendor configuration applies to exactly one driver.
 *
 * They are passed to the driver, but are not considered while allocating the claim.
 * @param selectors Each selector must be satisfied by a device which is claimed via this class.
 */
@Serializable
public data class DeviceClassSpec(
  public val config: List<DeviceClassConfiguration>,
  public val selectors: List<DeviceSelector>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

  @Transient
  override val group: String = "io.k8s.api.resource"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "DeviceClassSpec"
}