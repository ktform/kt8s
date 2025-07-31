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
 * @param apiServerID The ID of the reporting API server.
 * @param decodableVersions The API server can decode objects encoded in these versions. The encodingVersion must be included in the decodableVersions.
 * @param encodingVersion The API server encodes the object to this version when persisting it in the backend (e.g., etcd).
 * @param servedVersions The API server can serve these versions. DecodableVersions must include all ServedVersions.
 */
@Serializable
public data class ServerStorageVersion(
  public val apiServerID: String,
  public val decodableVersions: List<String>,
  public val encodingVersion: String,
  public val servedVersions: List<String>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.apiserverinternal/v1alpha1"

  @Transient
  override val group: String = "io.k8s.api.apiserverinternal"

  @Transient
  override val version: String = "v1alpha1"

  @SerialName("kind")
  override val kind: String = "ServerStorageVersion"
}