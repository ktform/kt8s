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
 * @param metadata The name is <group>.<resource>.
 * @param spec Spec is an empty spec. It is here to comply with Kubernetes API style.
 * @param status API server instances report the version they can decode and the version they encode objects to when persisting objects in the backend.
 */
@Serializable
public data class StorageVersion(
  public val metadata: ObjectMeta,
  public val spec: RawJsonObject,
  public val status: StorageVersionStatus,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "internal.apiserver.k8s.io/v1alpha1"

  @Transient
  override val group: String = "internal.apiserver.k8s.io"

  @Transient
  override val version: String = "v1alpha1"

  @SerialName("kind")
  override val kind: String = "StorageVersion"
}