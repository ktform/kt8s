package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param containerName Restricts the check for exit codes to the container with the specified name. When null, the rule applies to all containers. When specified, it should match one the container or initContainer names in the pod template.
 * @param operator Represents the relationship between the container exit code(s) and the specified values. Containers completed with success (exit code 0) are excluded from the requirement check. Possible values are:
 *
 * - In: the requirement is satisfied if at least one container exit code
 *   (might be multiple if there are multiple containers not restricted
 *   by the 'containerName' field) is in the set of specified values.
 * - NotIn: the requirement is satisfied if at least one container exit code
 *   (might be multiple if there are multiple containers not restricted
 *   by the 'containerName' field) is not in the set of specified values.
 * Additional values are considered to be added in the future. Clients should react to an unknown operator by assuming the requirement is not satisfied.
 * @param values Specifies the set of values. Each returned container exit code (might be multiple in case of multiple containers) is checked against this set of values with respect to the operator. The list of values must be ordered and must not contain duplicates. Value '0' cannot be used for the In operator. At least one element is required. At most 255 elements are allowed.
 */
@Serializable
public data class PodFailurePolicyOnExitCodesRequirement(
  public val containerName: String,
  public val `operator`: String,
  public val values: List<Int>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.batch/v1"

  @Transient
  override val group: String = "io.k8s.api.batch"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "PodFailurePolicyOnExitCodesRequirement"
}