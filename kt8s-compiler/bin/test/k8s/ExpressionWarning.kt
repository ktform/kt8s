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
 * @param fieldRef The path to the field that refers the expression. For example, the reference to the expression of the first item of validations is "spec.validations[0].expression"
 * @param warning The content of type checking information in a human-readable form. Each line of the warning contains the type that the expression is checked against, followed by the type check error from the compiler.
 */
@Serializable
public data class ExpressionWarning(
  public val fieldRef: String,
  public val warning: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.admissionregistration/v1"

  @Transient
  override val group: String = "io.k8s.api.admissionregistration"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ExpressionWarning"
}