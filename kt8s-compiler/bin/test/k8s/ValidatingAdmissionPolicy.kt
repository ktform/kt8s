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
 * @param spec Specification of the desired behavior of the ValidatingAdmissionPolicy.
 * @param status The status of the ValidatingAdmissionPolicy, including warnings that are useful to determine if the policy behaves in the expected way. Populated by the system. Read-only.
 */
@Serializable
public data class ValidatingAdmissionPolicy(
  public val metadata: ObjectMeta,
  public val spec: ValidatingAdmissionPolicySpec,
  public val status: ValidatingAdmissionPolicyStatus,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "admissionregistration.k8s.io/v1"

  @Transient
  override val group: String = "admissionregistration.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ValidatingAdmissionPolicy"
}