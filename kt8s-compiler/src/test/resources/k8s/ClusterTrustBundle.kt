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
 * @param metadata metadata contains the object metadata.
 * @param spec spec contains the signer (if any) and trust anchors.
 */
@Serializable
public data class ClusterTrustBundle(
  public val metadata: ObjectMeta,
  public val spec: ClusterTrustBundleSpec,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "certificates.k8s.io/v1alpha1"

  @Transient
  override val group: String = "certificates.k8s.io"

  @Transient
  override val version: String = "v1alpha1"

  @SerialName("kind")
  override val kind: String = "ClusterTrustBundle"
}