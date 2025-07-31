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
 * @param health Health of the resource. can be one of:
 *  - Healthy: operates as normal
 *  - Unhealthy: reported unhealthy. We consider this a temporary health issue
 *               since we do not have a mechanism today to distinguish
 *               temporary and permanent issues.
 *  - Unknown: The status cannot be determined.
 *             For example, Device Plugin got unregistered and hasn't been re-registered since.
 *
 * In future we may want to introduce the PermanentlyUnhealthy Status.
 * @param resourceID ResourceID is the unique identifier of the resource. See the ResourceID type for more information.
 */
@Serializable
public data class ResourceHealth(
  public val health: String,
  public val resourceID: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ResourceHealth"
}