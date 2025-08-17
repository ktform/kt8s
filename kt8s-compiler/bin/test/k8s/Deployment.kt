package dev.ktform.kt8s.resources

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param metadata Standard object's metadata. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec Specification of the desired behavior of the Deployment.
 * @param status Most recently observed status of the Deployment.
 */
@Serializable
public data class Deployment(
    public val metadata: ObjectMeta,
    public val spec: DeploymentSpec,
    public val status: DeploymentStatus,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "apps/v1"

    @Transient override val group: String = "apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Deployment"
}
