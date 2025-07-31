package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param exec Exec specifies a command to execute in the container.
 * @param failureThreshold Minimum consecutive failures for the probe to be considered failed after having succeeded. Defaults to 3. Minimum value is 1.
 * @param grpc GRPC specifies a GRPC HealthCheckRequest.
 * @param httpGet HTTPGet specifies an HTTP GET request to perform.
 * @param initialDelaySeconds Number of seconds after the container has started before liveness probes are initiated. More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#container-probes
 * @param periodSeconds How often (in seconds) to perform the probe. Default to 10 seconds. Minimum value is 1.
 * @param successThreshold Minimum consecutive successes for the probe to be considered successful after having failed. Defaults to 1. Must be 1 for liveness and startup. Minimum value is 1.
 * @param tcpSocket TCPSocket specifies a connection to a TCP port.
 * @param terminationGracePeriodSeconds Optional duration in seconds the pod needs to terminate gracefully upon probe failure. The grace period is the duration in seconds after the processes running in the pod are sent a termination signal and the time when the processes are forcibly halted with a kill signal. Set this value longer than the expected cleanup time for your process. If this value is nil, the pod's terminationGracePeriodSeconds will be used. Otherwise, this value overrides the value provided by the pod spec. Value must be non-negative integer. The value zero indicates stop immediately via the kill signal (no opportunity to shut down). This is a beta field and requires enabling ProbeTerminationGracePeriod feature gate. Minimum value is 1. spec.terminationGracePeriodSeconds is used if unset.
 * @param timeoutSeconds Number of seconds after which the probe times out. Defaults to 1 second. Minimum value is 1. More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#container-probes
 */
@Serializable
public data class Probe(
  public val exec: ExecAction,
  public val failureThreshold: Int,
  public val grpc: GRPCAction,
  public val httpGet: HTTPGetAction,
  public val initialDelaySeconds: Int,
  public val periodSeconds: Int,
  public val successThreshold: Int,
  public val tcpSocket: TCPSocketAction,
  public val terminationGracePeriodSeconds: Long,
  public val timeoutSeconds: Int,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "Probe"
}