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
 * @param architecture The Architecture reported by the node
 * @param bootID Boot ID reported by the node.
 * @param containerRuntimeVersion ContainerRuntime Version reported by the node through runtime remote API (e.g. containerd://1.4.2).
 * @param kernelVersion Kernel Version reported by the node from 'uname -r' (e.g. 3.16.0-0.bpo.4-amd64).
 * @param kubeProxyVersion Deprecated: KubeProxy Version reported by the node.
 * @param kubeletVersion Kubelet Version reported by the node.
 * @param machineID MachineID reported by the node. For unique machine identification in the cluster this field is preferred. Learn more from man(5) machine-id: http://man7.org/linux/man-pages/man5/machine-id.5.html
 * @param operatingSystem The Operating System reported by the node
 * @param osImage OS Image reported by the node from /etc/os-release (e.g. Debian GNU/Linux 7 (wheezy)).
 * @param swap Swap Info reported by the node.
 * @param systemUUID SystemUUID reported by the node. For unique machine identification MachineID is preferred. This field is specific to Red Hat hosts https://access.redhat.com/documentation/en-us/red_hat_subscription_management/1/html/rhsm/uuid
 */
@Serializable
public data class NodeSystemInfo(
  public val architecture: String,
  public val bootID: String,
  public val containerRuntimeVersion: String,
  public val kernelVersion: String,
  public val kubeProxyVersion: String,
  public val kubeletVersion: String,
  public val machineID: String,
  public val operatingSystem: String,
  public val osImage: String,
  public val swap: NodeSwapStatus,
  public val systemUUID: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "NodeSystemInfo"
}