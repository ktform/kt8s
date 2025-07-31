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
 * @param effect The effect of the taint on claims that do not tolerate the taint and through such claims on the pods using them. Valid effects are NoSchedule and NoExecute. PreferNoSchedule as used for nodes is not valid here.
 * @param key The taint key to be applied to a device. Must be a label name.
 * @param timeAdded TimeAdded represents the time at which the taint was added. Added automatically during create or update if not set.
 * @param value The taint value corresponding to the taint key. Must be a label value.
 */
@Serializable
public data class DeviceTaint(
  public val effect: String,
  public val key: String,
  public val timeAdded: KubernetesTime,
  public val `value`: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

  @Transient
  override val group: String = "io.k8s.api.resource"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "DeviceTaint"
}