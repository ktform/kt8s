package dev.ktform.kt8s.resources

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec Specification of the desired behavior of a job. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status
 * @param status Current status of a job. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status
 */
@Serializable
public data class Job(
    public val metadata: ObjectMeta,
    public val spec: JobSpec,
    public val status: JobStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "batch/v1"

    @Transient override val group: String = "batch"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Job"
}
