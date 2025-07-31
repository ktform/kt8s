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
 * @param additionalPrinterColumns additionalPrinterColumns specifies additional columns returned in Table output. See https://kubernetes.io/docs/reference/using-api/api-concepts/#receiving-resources-as-tables for details. If no columns are specified, a single column displaying the age of the custom resource is used.
 * @param deprecated deprecated indicates this version of the custom resource API is deprecated. When set to true, API requests to this version receive a warning header in the server response. Defaults to false.
 * @param deprecationWarning deprecationWarning overrides the default warning returned to API clients. May only be set when `deprecated` is true. The default warning indicates this version is deprecated and recommends use of the newest served version of equal or greater stability, if one exists.
 * @param name name is the version name, e.g. “v1”, “v2beta1”, etc. The custom resources are served under this version at `/apis/<group>/<version>/...` if `served` is true.
 * @param schema schema describes the schema used for validation, pruning, and defaulting of this version of the custom resource.
 * @param selectableFields selectableFields specifies paths to fields that may be used as field selectors. A maximum of 8 selectable fields are allowed. See https://kubernetes.io/docs/concepts/overview/working-with-objects/field-selectors
 * @param served served is a flag enabling/disabling this version from being served via REST APIs
 * @param storage storage indicates this version should be used when persisting custom resources to storage. There must be exactly one version with storage=true.
 * @param subresources subresources specify what subresources this version of the defined custom resource have.
 */
@Serializable
public data class CustomResourceDefinitionVersion(
  public val additionalPrinterColumns: List<CustomResourceColumnDefinition>,
  public val deprecated: Boolean,
  public val deprecationWarning: String,
  public val name: String,
  public val schema: CustomResourceValidation,
  public val selectableFields: List<SelectableField>,
  public val served: Boolean,
  public val storage: Boolean,
  public val subresources: CustomResourceSubresources,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

  @Transient
  override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CustomResourceDefinitionVersion"
}