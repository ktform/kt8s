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
 * @param scale scale indicates the custom resource should serve a `/scale` subresource that returns an `autoscaling/v1` Scale object.
 * @param status status indicates the custom resource should serve a `/status` subresource. When enabled: 1. requests to the custom resource primary endpoint ignore changes to the `status` stanza of the object. 2. requests to the custom resource `/status` subresource ignore changes to anything other than the `status` stanza of the object.
 */
@Serializable
public data class CustomResourceSubresources(
  public val scale: CustomResourceSubresourceScale,
  public val status: RawJsonObject,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions/v1"

  @Transient
  override val group: String = "io.k8s.apiextensions-apiserver.pkg.apis.apiextensions"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CustomResourceSubresources"
}