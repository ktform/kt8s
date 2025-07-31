package dev.ktform.kt8s.resources

import dev.ktform.kt8s.resources.IntOrString
import dev.ktform.kt8s.resources.KubernetesMicroTime
import dev.ktform.kt8s.resources.KubernetesTime
import dev.ktform.kt8s.resources.RawJsonObject
import dev.ktform.kt8s.resources.Resource
import dev.ktform.kt8s.resources.StringOrNumber
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param apiGroups APIGroups is the name of the APIGroup that contains the resources.  If multiple API groups are specified, any action requested against one of the enumerated resources in any API group will be allowed.  "*" means all.
 * @param resourceNames ResourceNames is an optional white list of names that the rule applies to.  An empty set means that everything is allowed.  "*" means all.
 * @param resources Resources is a list of resources this rule applies to.  "*" means all in the specified apiGroups.
 *  "*\/foo" represents the subresource 'foo' for all resources in the specified apiGroups.
 * @param verbs Verb is a list of kubernetes resource API verbs, like: get, list, watch, create, update, delete, proxy.  "*" means all.
 */
@Serializable
public data class ResourceRule(
  public val apiGroups: List<String>,
  public val resourceNames: List<String>,
  public val resources: List<String>,
  public val verbs: List<String>,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.authorization/v1"

  @Transient
  override val group: String = "io.k8s.api.authorization"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ResourceRule"
}