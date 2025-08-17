package dev.ktform.kt8s.resources

import kotlin.Int
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param port Port number of the gRPC service. Number must be in the range 1 to 65535.
 * @param service Service is the name of the service to place in the gRPC HealthCheckRequest (see
 *   https://github.com/grpc/grpc/blob/master/doc/health-checking.md).
 *
 * If this is not specified, the default behavior is defined by gRPC.
 */
@Serializable
public data class GRPCAction(public val port: Int, public val service: String) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "GRPCAction"
}
