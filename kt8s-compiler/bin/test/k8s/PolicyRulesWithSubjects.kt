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
 * @param nonResourceRules `nonResourceRules` is a list of NonResourcePolicyRules that identify matching requests according to their verb and the target non-resource URL.
 * @param resourceRules `resourceRules` is a slice of ResourcePolicyRules that identify matching requests according to their verb and the target resource. At least one of `resourceRules` and `nonResourceRules` has to be non-empty.
 * @param subjects subjects is the list of normal user, serviceaccount, or group that this rule cares about. There must be at least one member in this slice. A slice that includes both the system:authenticated and system:unauthenticated user groups matches every request. Required.
 */
@Serializable
public data class PolicyRulesWithSubjects(
  public val nonResourceRules: List<NonResourcePolicyRule>,
  public val resourceRules: List<ResourcePolicyRule>,
  public val subjects: List<Subject>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.flowcontrol/v1"

  @Transient
  override val group: String = "io.k8s.api.flowcontrol"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "PolicyRulesWithSubjects"
}