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
 * @param continueToken The token used in the list options to get the next chunk of objects to migrate. When the .status.conditions indicates the migration is "Running", users can use this token to check the progress of the migration.
 * @param resource The resource that is being migrated. The migrator sends requests to the endpoint serving the resource. Immutable.
 */
@Serializable
public data class StorageVersionMigrationSpec(
  public val continueToken: String,
  public val resource: GroupVersionResource,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.storagemigration/v1alpha1"

  @Transient
  override val group: String = "io.k8s.api.storagemigration"

  @Transient
  override val version: String = "v1alpha1"

  @SerialName("kind")
  override val kind: String = "StorageVersionMigrationSpec"
}