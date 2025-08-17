package dev.ktform.kt8s.resources

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param metadata Standard object metadata
 * @param spec Spec specifies the selector and one taint.
 *
 * Changing the spec automatically increments the metadata.generation number.
 */
@Serializable
public data class DeviceTaintRule(
    public val metadata: ObjectMeta,
    public val spec: DeviceTaintRuleSpec,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "resource.k8s.io/v1alpha3"

    @Transient override val group: String = "resource.k8s.io"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "DeviceTaintRule"
}
