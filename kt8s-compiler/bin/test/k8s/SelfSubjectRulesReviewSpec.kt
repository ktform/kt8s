package dev.ktform.kt8s.resources

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/** @param namespace Namespace to evaluate rules for. Required. */
@Serializable
public data class SelfSubjectRulesReviewSpec(public val namespace: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.authorization/v1"

    @Transient override val group: String = "io.k8s.api.authorization"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SelfSubjectRulesReviewSpec"
}
