package dev.ktform.kt8s.resources

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/** @param userInfo User attributes of the user making this request. */
@Serializable
public data class SelfSubjectReviewStatus(public val userInfo: UserInfo) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.authentication/v1"

    @Transient override val group: String = "io.k8s.api.authentication"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "SelfSubjectReviewStatus"
}
