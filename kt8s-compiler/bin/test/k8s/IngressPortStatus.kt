package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param error error is to record the problem with the service port The format of the error shall comply with the following rules: - built-in error values shall be specified in this file and those shall use
 *   CamelCase names
 * - cloud provider specific error values must have names that comply with the
 *   format foo.example.com/CamelCase.
 * @param port port is the port number of the ingress port.
 * @param protocol protocol is the protocol of the ingress port. The supported values are: "TCP", "UDP", "SCTP"
 */
@Serializable
public data class IngressPortStatus(
  public val error: String,
  public val port: Int,
  public val protocol: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.networking/v1"

  @Transient
  override val group: String = "io.k8s.api.networking"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "IngressPortStatus"
}