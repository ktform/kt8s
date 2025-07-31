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
 * @param object Object is:
 *  * If Type is Added or Modified: the new state of the object.
 *  * If Type is Deleted: the state of the object immediately before deletion.
 *  * If Type is Error: *Status is recommended; other types may make sense
 *    depending on context.
 */
@Serializable
public data class WatchEvent(
  public val `object`: RawJsonObject,
  public val type: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "v1"

  @Transient
  override val group: String = ""

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "WatchEvent"
}