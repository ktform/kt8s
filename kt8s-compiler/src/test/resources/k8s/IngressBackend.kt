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
 * @param resource resource is an ObjectRef to another Kubernetes resource in the namespace of the Ingress object. If resource is specified, a service.Name and service.Port must not be specified. This is a mutually exclusive setting with "Service".
 * @param service service references a service as a backend. This is a mutually exclusive setting with "Resource".
 */
@Serializable
public data class IngressBackend(
  public val resource: TypedLocalObjectReference,
  public val service: IngressServiceBackend,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.networking/v1"

  @Transient
  override val group: String = "io.k8s.api.networking"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "IngressBackend"
}