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
 * @param labelSelector Select all ClusterTrustBundles that match this label selector.  Only has effect if signerName is set.  Mutually-exclusive with name.  If unset, interpreted as "match nothing".  If set but empty, interpreted as "match everything".
 * @param name Select a single ClusterTrustBundle by object name.  Mutually-exclusive with signerName and labelSelector.
 * @param optional If true, don't block pod startup if the referenced ClusterTrustBundle(s) aren't available.  If using name, then the named ClusterTrustBundle is allowed not to exist.  If using signerName, then the combination of signerName and labelSelector is allowed to match zero ClusterTrustBundles.
 * @param path Relative path from the volume root to write the bundle.
 * @param signerName Select all ClusterTrustBundles that match this signer name. Mutually-exclusive with name.  The contents of all selected ClusterTrustBundles will be unified and deduplicated.
 */
@Serializable
public data class ClusterTrustBundleProjection(
  public val labelSelector: LabelSelector,
  public val name: String,
  public val optional: Boolean,
  public val path: String,
  public val signerName: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.core/v1"

  @Transient
  override val group: String = "io.k8s.api.core"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "ClusterTrustBundleProjection"
}