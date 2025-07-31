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
 * @param lastTransitionTime lastTransitionTime is the time the condition last transitioned from one status to another. If unset, when a new condition type is added or an existing condition's status is changed, the server defaults this to the current time.
 * @param lastUpdateTime lastUpdateTime is the time of the last update to this condition
 * @param message message contains a human readable message with details about the request state
 * @param reason reason indicates a brief reason for the request state
 * @param status status of the condition, one of True, False, Unknown. Approved, Denied, and Failed conditions may not be "False" or "Unknown".
 * @param type type of the condition. Known conditions are "Approved", "Denied", and "Failed".
 *
 * An "Approved" condition is added via the /approval subresource, indicating the request was approved and should be issued by the signer.
 *
 * A "Denied" condition is added via the /approval subresource, indicating the request was denied and should not be issued by the signer.
 *
 * A "Failed" condition is added via the /status subresource, indicating the signer failed to issue the certificate.
 *
 * Approved and Denied conditions are mutually exclusive. Approved, Denied, and Failed conditions cannot be removed once added.
 *
 * Only one condition of a given type is allowed.
 */
@Serializable
public data class CertificateSigningRequestCondition(
  public val lastTransitionTime: KubernetesTime,
  public val lastUpdateTime: KubernetesTime,
  public val message: String,
  public val reason: String,
  public val status: String,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.certificates/v1"

  @Transient
  override val group: String = "io.k8s.api.certificates"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CertificateSigningRequestCondition"
}