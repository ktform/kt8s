package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param allocatedResources AllocatedResources represents the compute resources allocated for this container by the node. Kubelet sets this value to Container.Resources.Requests upon successful pod admission and after successfully admitting desired pod resize.
 * @param allocatedResourcesStatus AllocatedResourcesStatus represents the status of various resources allocated for this Pod.
 * @param containerID ContainerID is the ID of the container in the format '<type>://<container_id>'. Where type is a container runtime identifier, returned from Version call of CRI API (for example "containerd").
 * @param image Image is the name of container image that the container is running. The container image may not match the image used in the PodSpec, as it may have been resolved by the runtime. More info: https://kubernetes.io/docs/concepts/containers/images.
 * @param imageID ImageID is the image ID of the container's image. The image ID may not match the image ID of the image used in the PodSpec, as it may have been resolved by the runtime.
 * @param lastState LastTerminationState holds the last termination state of the container to help debug container crashes and restarts. This field is not populated if the container is still running and RestartCount is 0.
 * @param name Name is a DNS_LABEL representing the unique name of the container. Each container in a pod must have a unique name across all container types. Cannot be updated.
 * @param ready Ready specifies whether the container is currently passing its readiness check. The value will change as readiness probes keep executing. If no readiness probes are specified, this field defaults to true once the container is fully started (see Started field).
 *
 * The value is typically used to determine whether a container is ready to accept traffic.
 * @param resources Resources represents the compute resource requests and limits that have been successfully enacted on the running container after it has been started or has been successfully resized.
 * @param restartCount RestartCount holds the number of times the container has been restarted. Kubelet makes an effort to always increment the value, but there are cases when the state may be lost due to node restarts and then the value may be reset to 0. The value is never negative.
 * @param started Started indicates whether the container has finished its postStart lifecycle hook and passed its startup probe. Initialized as false, becomes true after startupProbe is considered successful. Resets to false when the container is restarted, or if kubelet loses state temporarily. In both cases, startup probes will run again. Is always true when no startupProbe is defined and container is running and has passed the postStart lifecycle hook. The null value must be treated the same as false.
 * @param state State holds details about the container's current condition.
 * @param stopSignal StopSignal reports the effective stop signal for this container
 * @param user User represents user identity information initially attached to the first process of the container
 * @param volumeMounts Status of volume mounts.
 */
@Serializable
public data class ContainerStatus(
  public val allocatedResources: StringOrNumber,
  public val allocatedResourcesStatus: List<ResourceStatus>,
  public val containerID: String,
  public val image: String,
  public val imageID: String,
  public val lastState: ContainerState,
  public val name: String,
  public val ready: Boolean,
  public val resources: ResourceRequirements,
  public val restartCount: Int,
  public val started: Boolean,
  public val state: ContainerState,
  public val stopSignal: String,
  public val user: ContainerUser,
  public val volumeMounts: List<VolumeMountStatus>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ContainerStatus"
}