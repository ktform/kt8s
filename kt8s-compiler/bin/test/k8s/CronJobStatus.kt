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
 * @param active A list of pointers to currently running jobs.
 * @param lastScheduleTime Information when was the last time the job was successfully scheduled.
 * @param lastSuccessfulTime Information when was the last time the job successfully completed.
 */
@Serializable
public data class CronJobStatus(
  public val active: List<ObjectReference>,
  public val lastScheduleTime: KubernetesTime,
  public val lastSuccessfulTime: KubernetesTime,
) : Resource {
  @SerialName("apiVersion")
  override val apiVersion: String = "io.k8s.api.batch/v1"

  @Transient
  override val group: String = "io.k8s.api.batch"

  @Transient
  override val version: String = "v1"

  @SerialName("kind")
  override val kind: String = "CronJobStatus"
}