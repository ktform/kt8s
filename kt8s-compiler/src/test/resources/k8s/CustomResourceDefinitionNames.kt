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
 * @param categories categories is a list of grouped resources this custom resource belongs to (e.g. 'all'). This is published in API discovery documents, and used by clients to support invocations like `kubectl get all`.
 * @param listKind listKind is the serialized kind of the list for this resource. Defaults to "`kind`List".
 * @param plural plural is the plural name of the resource to serve. The custom resources are served under `/apis/<group>/<version>/.../<plural>`. Must match the name of the CustomResourceDefinition (in the form `<names.plural>.<group>`). Must be all lowercase.
 * @param shortNames shortNames are short names for the resource, exposed in API discovery documents, and used by clients to support invocations like `kubectl get <shortname>`. It must be all lowercase.
 * @param singular singular is the singular name of the resource. It must be all lowercase. Defaults to lowercased `kind`.
 */
@Serializable
public data class CustomResourceDefinitionNames(
  public val categories: List<String>,
  public val listKind: String,
  public val plural: String,
  public val shortNames: List<String>,
  public val singular: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

  @Transient
  override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CustomResourceDefinitionNames"
}