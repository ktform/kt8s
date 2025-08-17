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

import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param minReadySeconds Minimum number of seconds for which a newly created pod should be ready
 *   without any of its container crashing for it to be considered available. Defaults to 0 (pod
 *   will be considered available as soon as it is ready)
 * @param ordinals ordinals controls the numbering of replica indices in a StatefulSet. The default
 *   ordinals behavior assigns a "0" index to the first replica and increments the index by one for
 *   each additional replica requested.
 * @param persistentVolumeClaimRetentionPolicy persistentVolumeClaimRetentionPolicy describes the
 *   lifecycle of persistent volume claims created from volumeClaimTemplates. By default, all
 *   persistent volume claims are created as needed and retained until manually deleted. This policy
 *   allows the lifecycle to be altered, for example by deleting persistent volume claims when their
 *   stateful set is deleted, or when their pod is scaled down.
 * @param podManagementPolicy podManagementPolicy controls how pods are created during initial scale
 *   up, when replacing pods on nodes, or when scaling down. The default policy is `OrderedReady`,
 *   where pods are created in increasing order (pod-0, then pod-1, etc) and the controller will
 *   wait until each pod is ready before continuing. When scaling down, the pods are removed in the
 *   opposite order. The alternative policy is `Parallel` which will create pods in parallel to
 *   match the desired scale without waiting, and on scale down will delete all pods at once.
 * @param replicas replicas is the desired number of replicas of the given Template. These are
 *   replicas in the sense that they are instantiations of the same Template, but individual
 *   replicas also have a consistent identity. If unspecified, defaults to 1.
 * @param revisionHistoryLimit revisionHistoryLimit is the maximum number of revisions that will be
 *   maintained in the StatefulSet's revision history. The revision history consists of all
 *   revisions not represented by a currently applied StatefulSetSpec version. The default value
 *   is 10.
 * @param selector selector is a label query over pods that should match the replica count. It must
 *   match the pod template's labels. More info:
 *   https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/#label-selectors
 * @param serviceName serviceName is the name of the service that governs this StatefulSet. This
 *   service must exist before the StatefulSet, and is responsible for the network identity of the
 *   set. Pods get DNS/hostnames that follow the pattern:
 *   pod-specific-string.serviceName.default.svc.cluster.local where "pod-specific-string" is
 *   managed by the StatefulSet controller.
 * @param template template is the object that describes the pod that will be created if
 *   insufficient replicas are detected. Each pod stamped out by the StatefulSet will fulfill this
 *   Template, but have a unique identity from the rest of the StatefulSet. Each pod will be named
 *   with the format <statefulsetname>-<podindex>. For example, a pod in a StatefulSet named "web"
 *   with index number "3" would be named "web-3". The only allowed template.spec.restartPolicy
 *   value is "Always".
 * @param updateStrategy updateStrategy indicates the StatefulSetUpdateStrategy that will be
 *   employed to update Pods in the StatefulSet when a revision is made to Template.
 * @param volumeClaimTemplates volumeClaimTemplates is a list of claims that pods are allowed to
 *   reference. The StatefulSet controller is responsible for mapping network identities to claims
 *   in a way that maintains the identity of a pod. Every claim in this list must have at least one
 *   matching (by name) volumeMount in one container in the template. A claim in this list takes
 *   precedence over any volumes in the template, with the same name.
 */
@Serializable
public data class StatefulSetSpec(
    public val minReadySeconds: Int,
    public val ordinals: StatefulSetOrdinals,
    public val persistentVolumeClaimRetentionPolicy:
        StatefulSetPersistentVolumeClaimRetentionPolicy,
    public val podManagementPolicy: String,
    public val replicas: Int,
    public val revisionHistoryLimit: Int,
    public val selector: LabelSelector,
    public val serviceName: String,
    public val template: PodTemplateSpec,
    public val updateStrategy: StatefulSetUpdateStrategy,
    public val volumeClaimTemplates: List<PersistentVolumeClaim>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.apps/v1"

    @Transient override val group: String = "io.k8s.api.apps"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "StatefulSetSpec"
}
