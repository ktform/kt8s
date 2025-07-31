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
 * @param binaryVersion BinaryVersion is the binary version. It must be in a semver format without leading `v`. This field is required.
 * @param emulationVersion EmulationVersion is the emulation version. It must be in a semver format without leading `v`. EmulationVersion must be less than or equal to BinaryVersion. This field is required when strategy is "OldestEmulationVersion"
 * @param leaseName LeaseName is the name of the lease for which this candidate is contending. This field is immutable.
 * @param pingTime PingTime is the last time that the server has requested the LeaseCandidate to renew. It is only done during leader election to check if any LeaseCandidates have become ineligible. When PingTime is updated, the LeaseCandidate will respond by updating RenewTime.
 * @param renewTime RenewTime is the time that the LeaseCandidate was last updated. Any time a Lease needs to do leader election, the PingTime field is updated to signal to the LeaseCandidate that they should update the RenewTime. Old LeaseCandidate objects are also garbage collected if it has been hours since the last renew. The PingTime field is updated regularly to prevent garbage collection for still active LeaseCandidates.
 * @param strategy Strategy is the strategy that coordinated leader election will use for picking the leader. If multiple candidates for the same Lease return different strategies, the strategy provided by the candidate with the latest BinaryVersion will be used. If there is still conflict, this is a user error and coordinated leader election will not operate the Lease until resolved.
 */
@Serializable
public data class LeaseCandidateSpec(
  public val binaryVersion: String,
  public val emulationVersion: String,
  public val leaseName: String,
  public val pingTime: KubernetesMicroTime,
  public val renewTime: KubernetesMicroTime,
  public val strategy: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.coordination/v1alpha2"

  @Transient
  override val group: String = "io.k8s.api.coordination"

  @Transient
  override val version: String = "v1alpha2"

  @SerialName("kind")
  override val kind: String = "LeaseCandidateSpec"
}