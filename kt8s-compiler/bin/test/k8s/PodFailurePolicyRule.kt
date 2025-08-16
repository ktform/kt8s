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
 * @param action Specifies the action taken on a pod failure when the requirements are satisfied. Possible values are:
 *
 * - FailJob: indicates that the pod's job is marked as Failed and all
 *   running pods are terminated.
 * - FailIndex: indicates that the pod's index is marked as Failed and will
 *   not be restarted.
 * - Ignore: indicates that the counter towards the .backoffLimit is not
 *   incremented and a replacement pod is created.
 * - Count: indicates that the pod is handled in the default way - the
 *   counter towards the .backoffLimit is incremented.
 * Additional values are considered to be added in the future. Clients should react to an unknown action by skipping the rule.
 * @param onExitCodes Represents the requirement on the container exit codes.
 * @param onPodConditions Represents the requirement on the pod conditions. The requirement is represented as a list of pod condition patterns. The requirement is satisfied if at least one pattern matches an actual pod condition. At most 20 elements are allowed.
 */
@Serializable
public data class PodFailurePolicyRule(
  public val action: String,
  public val onExitCodes: PodFailurePolicyOnExitCodesRequirement,
  public val onPodConditions: List<PodFailurePolicyOnPodConditionsPattern>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.batch/v1"

  @Transient
  override val group: String = "io.k8s.api.batch"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "PodFailurePolicyRule"
}