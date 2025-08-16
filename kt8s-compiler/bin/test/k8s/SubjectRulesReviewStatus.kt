package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param evaluationError EvaluationError can appear in combination with Rules. It indicates an error occurred during rule evaluation, such as an authorizer that doesn't support rule evaluation, and that ResourceRules and/or NonResourceRules may be incomplete.
 * @param incomplete Incomplete is true when the rules returned by this call are incomplete. This is most commonly encountered when an authorizer, such as an external authorizer, doesn't support rules evaluation.
 * @param nonResourceRules NonResourceRules is the list of actions the subject is allowed to perform on non-resources. The list ordering isn't significant, may contain duplicates, and possibly be incomplete.
 * @param resourceRules ResourceRules is the list of actions the subject is allowed to perform on resources. The list ordering isn't significant, may contain duplicates, and possibly be incomplete.
 */
@Serializable
public data class SubjectRulesReviewStatus(
  public val evaluationError: String,
  public val incomplete: Boolean,
  public val nonResourceRules: List<NonResourceRule>,
  public val resourceRules: List<ResourceRule>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.authorization/v1"

  @Transient
  override val group: String = "io.k8s.api.authorization"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "SubjectRulesReviewStatus"
}