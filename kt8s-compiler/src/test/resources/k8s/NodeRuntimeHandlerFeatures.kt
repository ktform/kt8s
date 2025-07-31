package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param recursiveReadOnlyMounts RecursiveReadOnlyMounts is set to true if the runtime handler supports RecursiveReadOnlyMounts.
 * @param userNamespaces UserNamespaces is set to true if the runtime handler supports UserNamespaces, including for volumes.
 */
@Serializable
public data class NodeRuntimeHandlerFeatures(
  public val recursiveReadOnlyMounts: Boolean,
  public val userNamespaces: Boolean,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "NodeRuntimeHandlerFeatures"
}