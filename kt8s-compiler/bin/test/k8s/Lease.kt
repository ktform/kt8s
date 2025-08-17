package dev.ktform.kt8s.resources

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param metadata More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
 * @param spec spec contains the specification of the Lease. More info:
 *   https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#spec-and-status
 */
@Serializable
public data class Lease(public val metadata: ObjectMeta, public val spec: LeaseSpec) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "coordination.k8s.io/v1"

    @Transient override val group: String = "coordination.k8s.io"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Lease"
}
