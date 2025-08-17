package dev.ktform.kt8s.resources

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param name name is the name of the port on the Service. This is a mutually exclusive setting
 *   with "Number".
 * @param number number is the numerical port number (e.g. 80) on the Service. This is a mutually
 *   exclusive setting with "Name".
 */
@Serializable
public data class ServiceBackendPort(public val name: String, public val number: Int) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.networking/v1"

    @Transient override val group: String = "io.k8s.api.networking"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "ServiceBackendPort"
}
