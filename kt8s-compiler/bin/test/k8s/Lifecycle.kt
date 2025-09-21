/*
 * Copyright (C) 2016-2025 Yuriy Yarosh
 * All rights reserved.
 *
 * SPDX-License-Identifier: MPL-2.0
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.ktform.kt8s.resources

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param postStart PostStart is called immediately after a container is created. If the handler
 *   fails, the container is terminated and restarted according to its restart policy. Other
 *   management of the container blocks until the hook completes. More info:
 *   https://kubernetes.io/docs/concepts/containers/container-lifecycle-hooks/#container-hooks
 * @param preStop PreStop is called immediately before a container is terminated due to an API
 *   request or management event such as liveness/startup probe failure, preemption, resource
 *   contention, etc. The handler is not called if the container crashes or exits. The Pod's
 *   termination grace period countdown begins before the PreStop hook is executed. Regardless of
 *   the outcome of the handler, the container will eventually terminate within the Pod's
 *   termination grace period (unless delayed by finalizers). Other management of the container
 *   blocks until the hook completes or until the termination grace period is reached. More info:
 *   https://kubernetes.io/docs/concepts/containers/container-lifecycle-hooks/#container-hooks
 * @param stopSignal StopSignal defines which signal will be sent to a container when it is being
 *   stopped. If not specified, the default is defined by the container runtime in use. StopSignal
 *   can only be set for Pods with a non-empty .spec.os.name
 */
@Serializable
public data class Lifecycle(
    public val postStart: LifecycleHandler,
    public val preStop: LifecycleHandler,
    public val stopSignal: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "Lifecycle"
}
