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
 * @param matchResources matchResources limits what resources match this binding and may be mutated
 *   by it. Note that if matchResources matches a resource, the resource must also match a policy's
 *   matchConstraints and matchConditions before the resource may be mutated. When matchResources is
 *   unset, it does not constrain resource matching, and only the policy's matchConstraints and
 *   matchConditions must match for the resource to be mutated. Additionally,
 *   matchResources.resourceRules are optional and do not constraint matching when unset. Note that
 *   this is differs from MutatingAdmissionPolicy matchConstraints, where resourceRules are
 *   required. The CREATE, UPDATE and CONNECT operations are allowed. The DELETE operation may not
 *   be matched. '*' matches CREATE, UPDATE and CONNECT.
 * @param paramRef paramRef specifies the parameter resource used to configure the admission control
 *   policy. It should point to a resource of the type specified in spec.ParamKind of the bound
 *   MutatingAdmissionPolicy. If the policy specifies a ParamKind and the resource referred to by
 *   ParamRef does not exist, this binding is considered mis-configured and the FailurePolicy of the
 *   MutatingAdmissionPolicy applied. If the policy does not specify a ParamKind then this field is
 *   ignored, and the rules are evaluated without a param.
 * @param policyName policyName references a MutatingAdmissionPolicy name which the
 *   MutatingAdmissionPolicyBinding binds to. If the referenced resource does not exist, this
 *   binding is considered invalid and will be ignored Required.
 */
@Serializable
public data class MutatingAdmissionPolicyBindingSpec(
    public val matchResources: MatchResources,
    public val paramRef: ParamRef,
    public val policyName: String,
) : Resource {
    @SerialName("apiVersion")
    override val apiVersion: String = "io.k8s.api.admissionregistration/v1alpha1"

    @Transient override val group: String = "io.k8s.api.admissionregistration"

    @Transient override val version: String = "v1alpha1"

    @SerialName("kind") override val kind: String = "MutatingAdmissionPolicyBindingSpec"
}
