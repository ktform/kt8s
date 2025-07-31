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
 * @param blockOwnerDeletion If true, AND if the owner has the "foregroundDeletion" finalizer, then the owner cannot be deleted from the key-value store until this reference is removed. See https://kubernetes.io/docs/concepts/architecture/garbage-collection/#foreground-deletion for how the garbage collector interacts with this field and enforces the foreground deletion. Defaults to false. To set this field, a user needs "delete" permission of the owner, otherwise 422 (Unprocessable Entity) will be returned.
 * @param controller If true, this reference points to the managing controller.
 * @param name Name of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names#names
 * @param uid UID of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names#uids
 */
@Serializable
public data class OwnerReference(
  public val blockOwnerDeletion: Boolean,
  public val controller: Boolean,
  public val name: String,
  public val uid: String,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.apimachinery.pkg.apis.meta/v1"

  @Transient
  override val group: String = "io.k8s.apimachinery.pkg.apis.meta"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "OwnerReference"
}