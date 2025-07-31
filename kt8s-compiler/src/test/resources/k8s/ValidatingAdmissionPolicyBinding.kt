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
 * @param metadata Standard object metadata; More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata.
 * @param spec Specification of the desired behavior of the ValidatingAdmissionPolicyBinding.
 */
@Serializable
public data class ValidatingAdmissionPolicyBinding(
  public val metadata: ObjectMeta,
  public val spec: ValidatingAdmissionPolicyBindingSpec,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "admissionregistration.k8s.io/v1"

  @Transient
  override val group: String = "admissionregistration.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ValidatingAdmissionPolicyBinding"
}