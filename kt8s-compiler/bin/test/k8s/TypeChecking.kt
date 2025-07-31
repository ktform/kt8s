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
 * @param expressionWarnings The type checking warnings for each expression.
 */
@Serializable
public data class TypeChecking(
  public val expressionWarnings: List<ExpressionWarning>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

  @Transient
  override val group: String = "io.k8s.api.admissionregistration"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "TypeChecking"
}