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
 * @param spec spec contains the certificate request, and is immutable after creation. Only the request, signerName, expirationSeconds, and usages fields can be set on creation. Other fields are derived by Kubernetes and cannot be modified by users.
 * @param status status contains information about whether the request is approved or denied, and the certificate issued by the signer, or the failure condition indicating signer failure.
 */
@Serializable
public data class CertificateSigningRequest(
  public val metadata: ObjectMeta,
  public val spec: CertificateSigningRequestSpec,
  public val status: CertificateSigningRequestStatus,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "certificates.k8s.io/v1"

  @Transient
  override val group: String = "certificates.k8s.io"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CertificateSigningRequest"
}