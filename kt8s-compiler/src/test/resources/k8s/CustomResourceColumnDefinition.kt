package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param description description is a human readable description of this column.
 * @param format format is an optional OpenAPI type definition for this column. The 'name' format is applied to the primary identifier column to assist in clients identifying column is the resource name. See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#data-types for details.
 * @param jsonPath jsonPath is a simple JSON path (i.e. with array notation) which is evaluated against each custom resource to produce the value for this column.
 * @param name name is a human readable name for the column.
 * @param priority priority is an integer defining the relative importance of this column compared to others. Lower numbers are considered higher priority. Columns that may be omitted in limited space scenarios should be given a priority greater than 0.
 * @param type type is an OpenAPI type definition for this column. See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#data-types for details.
 */
@Serializable
public data class CustomResourceColumnDefinition(
  public val description: String,
  public val format: String,
  public val jsonPath: String,
  public val name: String,
  public val priority: Int,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

  @Transient
  override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CustomResourceColumnDefinition"
}