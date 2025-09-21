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

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param gmsaCredentialSpec GMSACredentialSpec is where the GMSA admission webhook
 *   (https://github.com/kubernetes-sigs/windows-gmsa) inlines the contents of the GMSA credential
 *   spec named by the GMSACredentialSpecName field.
 * @param gmsaCredentialSpecName GMSACredentialSpecName is the name of the GMSA credential spec to
 *   use.
 * @param hostProcess HostProcess determines if a container should be run as a 'Host Process'
 *   container. All of a Pod's containers must have the same effective HostProcess value (it is not
 *   allowed to have a mix of HostProcess containers and non-HostProcess containers). In addition,
 *   if HostProcess is true then HostNetwork must also be set to true.
 * @param runAsUserName The UserName in Windows to run the entrypoint of the container process.
 *   Defaults to the user specified in image metadata if unspecified. May also be set in
 *   PodSecurityContext. If set in both SecurityContext and PodSecurityContext, the value specified
 *   in SecurityContext takes precedence.
 */
@Serializable
public data class WindowsSecurityContextOptions(
    public val gmsaCredentialSpec: String,
    public val gmsaCredentialSpecName: String,
    public val hostProcess: Boolean,
    public val runAsUserName: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "WindowsSecurityContextOptions"
}
