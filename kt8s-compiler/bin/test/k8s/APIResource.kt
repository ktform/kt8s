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
 * @param categories categories is a list of the grouped resources this resource belongs to (e.g. 'all')
 * @param name name is the plural name of the resource.
 * @param namespaced namespaced indicates if a resource is namespaced or not.
 * @param shortNames shortNames is a list of suggested short names of the resource.
 * @param singularName singularName is the singular name of the resource.  This allows clients to handle plural and singular opaquely. The singularName is more correct for reporting status on a single item and both singular and plural are allowed from the kubectl CLI interface.
 * @param storageVersionHash The hash value of the storage version, the version this resource is converted to when written to the data store. Value must be treated as opaque by clients. Only equality comparison on the value is valid. This is an alpha feature and may change or be removed in the future. The field is populated by the apiserver only if the StorageVersionHash feature gate is enabled. This field will remain optional even if it graduates.
 * @param verbs verbs is a list of supported kube verbs (this includes get, list, watch, create, update, patch, delete, deletecollection, and proxy)
 */
@Serializable
public data class APIResource(
  public val categories: List<String>,
  public val name: String,
  public val namespaced: Boolean,
  public val shortNames: List<String>,
  public val singularName: String,
  public val storageVersionHash: String,
  public val verbs: List<String>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

  @Transient
  override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "APIResource"
}