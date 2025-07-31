package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param conditions The conditions represent the latest available observations of a policy's current state.
 * @param observedGeneration The generation observed by the controller.
 * @param typeChecking The results of type checking for each expression. Presence of this field indicates the completion of the type checking.
 */
@Serializable
public data class ValidatingAdmissionPolicyStatus(
  public val conditions: List<Condition>,
  public val observedGeneration: Long,
  public val typeChecking: TypeChecking,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

  @Transient
  override val group: String = "io.k8s.api.admissionregistration"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ValidatingAdmissionPolicyStatus"
}