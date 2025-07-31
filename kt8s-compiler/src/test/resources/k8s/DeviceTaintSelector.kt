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
 * @param device If device is set, only devices with that name are selected. This field corresponds to slice.spec.devices[].name.
 *
 * Setting also driver and pool may be required to avoid ambiguity, but is not required.
 * @param deviceClassName If DeviceClassName is set, the selectors defined there must be satisfied by a device to be selected. This field corresponds to class.metadata.name.
 * @param driver If driver is set, only devices from that driver are selected. This fields corresponds to slice.spec.driver.
 * @param pool If pool is set, only devices in that pool are selected.
 *
 * Also setting the driver name may be useful to avoid ambiguity when different drivers use the same pool name, but this is not required because selecting pools from different drivers may also be useful, for example when drivers with node-local devices use the node name as their pool name.
 * @param selectors Selectors contains the same selection criteria as a ResourceClaim. Currently, CEL expressions are supported. All of these selectors must be satisfied.
 */
@Serializable
public data class DeviceTaintSelector(
  public val device: String,
  public val deviceClassName: String,
  public val driver: String,
  public val pool: String,
  public val selectors: List<DeviceSelector>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

  @Transient
  override val group: String = "io.k8s.api.resource"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "DeviceTaintSelector"
}