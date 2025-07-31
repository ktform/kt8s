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
 * @param metadata Standard object metadata
 * @param spec Describes the ResourceClaim that is to be generated.
 *
 * This field is immutable. A ResourceClaim will get created by the control plane for a Pod when needed and then not get updated anymore.
 */
@Serializable
public data class ResourceClaimTemplate(
  public val metadata: ObjectMeta,
  public val spec: ResourceClaimTemplateSpec,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "resource.k8s.io/v1alpha3"

  @Transient
  override val group: String = "resource.k8s.io"

  @Transient
  override val version: String = "v1alpha3"

  @SerialName("kind")
  override val kind: String = "ResourceClaimTemplate"
}