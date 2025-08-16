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
 * @param acquireTime acquireTime is a time when the current lease was acquired.
 * @param holderIdentity holderIdentity contains the identity of the holder of a current lease. If Coordinated Leader Election is used, the holder identity must be equal to the elected LeaseCandidate.metadata.name field.
 * @param leaseDurationSeconds leaseDurationSeconds is a duration that candidates for a lease need to wait to force acquire it. This is measured against the time of last observed renewTime.
 * @param leaseTransitions leaseTransitions is the number of transitions of a lease between holders.
 * @param preferredHolder PreferredHolder signals to a lease holder that the lease has a more optimal holder and should be given up. This field can only be set if Strategy is also set.
 * @param renewTime renewTime is a time when the current holder of a lease has last updated the lease.
 * @param strategy Strategy indicates the strategy for picking the leader for coordinated leader election. If the field is not specified, there is no active coordination for this lease. (Alpha) Using this field requires the CoordinatedLeaderElection feature gate to be enabled.
 */
@Serializable
public data class LeaseSpec(
  public val acquireTime: KubernetesMicroTime,
  public val holderIdentity: String,
  public val leaseDurationSeconds: Int,
  public val leaseTransitions: Int,
  public val preferredHolder: String,
  public val renewTime: KubernetesMicroTime,
  public val strategy: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.coordination/v1"

  @Transient
  override val group: String = "io.k8s.api.coordination"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "LeaseSpec"
}