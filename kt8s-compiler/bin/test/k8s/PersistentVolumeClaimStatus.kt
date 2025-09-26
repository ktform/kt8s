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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param accessModes accessModes contains the actual access modes the volume backing the PVC has.
 *   More info: https://kubernetes.io/docs/concepts/storage/persistent-volumes#access-modes-1
 * @param allocatedResourceStatuses allocatedResourceStatuses stores status of resource being
 *   resized for the given PVC. Key names follow standard Kubernetes label syntax. Valid values are
 *   either:
 *     * Un-prefixed keys:
 *         - storage - the capacity of the volume.
 *     * Custom resources must use implementation-defined prefixed names such as
 *       "example.com/my-custom-resource" Apart from above values - keys that are unprefixed or have
 *       kubernetes.io prefix are considered reserved and hence may not be used.
 *
 * ClaimResourceStatus can be in any of following states:
 *     - ControllerResizeInProgress: State set when resize controller starts resizing the volume in
 *       control-plane.
 * - ControllerResizeFailed: State set when resize has failed in resize controller with a terminal
 *   error.
 * - NodeResizePending: State set when resize controller has finished resizing the volume but
 *   further resizing of volume is needed on the node.
 * - NodeResizeInProgress: State set when kubelet starts resizing the volume.
 * - NodeResizeFailed: State set when resizing has failed in kubelet with a terminal error.
 *   Transient errors don't set NodeResizeFailed. For example: if expanding a PVC for more
 *   capacity - this field can be one of the following states:
 * - pvc.status.allocatedResourceStatus['storage'] = "ControllerResizeInProgress"
 *             - pvc.status.allocatedResourceStatus['storage'] = "ControllerResizeFailed"
 *             - pvc.status.allocatedResourceStatus['storage'] = "NodeResizePending"
 *             - pvc.status.allocatedResourceStatus['storage'] = "NodeResizeInProgress"
 *             - pvc.status.allocatedResourceStatus['storage'] = "NodeResizeFailed" When this field
 *               is not set, it means that no resize operation is in progress for the given PVC.
 *
 * A controller that receives PVC update with previously unknown resourceName or ClaimResourceStatus
 * should ignore the update for the purpose it was designed. For example - a controller that only is
 * responsible for resizing capacity of the volume, should ignore PVC updates that change other
 * valid resources associated with PVC.
 *
 * This is an alpha field and requires enabling RecoverVolumeExpansionFailure feature.
 *
 * @param allocatedResources allocatedResources tracks the resources allocated to a PVC including
 *   its capacity. Key names follow standard Kubernetes label syntax. Valid values are either:
 *     * Un-prefixed keys:
 *         - storage - the capacity of the volume.
 *     * Custom resources must use implementation-defined prefixed names such as
 *       "example.com/my-custom-resource" Apart from above values - keys that are unprefixed or have
 *       kubernetes.io prefix are considered reserved and hence may not be used.
 *
 * Capacity reported here may be larger than the actual capacity when a volume expansion operation
 * is requested. For storage quota, the larger value from allocatedResources and PVC.spec.resources
 * is used. If allocatedResources is not set, PVC.spec.resources alone is used for quota
 * calculation. If a volume expansion capacity request is lowered, allocatedResources is only
 * lowered if there are no expansion operations in progress and if the actual volume capacity is
 * equal or lower than the requested capacity.
 *
 * A controller that receives PVC update with previously unknown resourceName should ignore the
 * update for the purpose it was designed. For example - a controller that only is responsible for
 * resizing capacity of the volume, should ignore PVC updates that change other valid resources
 * associated with PVC.
 *
 * This is an alpha field and requires enabling RecoverVolumeExpansionFailure feature.
 *
 * @param capacity capacity represents the actual resources of the underlying volume.
 * @param conditions conditions is the current Condition of persistent volume claim. If underlying
 *   persistent volume is being resized then the Condition will be set to 'Resizing'.
 * @param currentVolumeAttributesClassName currentVolumeAttributesClassName is the current name of
 *   the VolumeAttributesClass the PVC is using. When unset, there is no VolumeAttributeClass
 *   applied to this PersistentVolumeClaim This is a beta field and requires enabling
 *   VolumeAttributesClass feature (off by default).
 * @param modifyVolumeStatus ModifyVolumeStatus represents the status object of
 *   ControllerModifyVolume operation. When this is unset, there is no ModifyVolume operation being
 *   attempted. This is a beta field and requires enabling VolumeAttributesClass feature (off by
 *   default).
 * @param phase phase represents the current phase of PersistentVolumeClaim.
 */
@Serializable
public data class PersistentVolumeClaimStatus(
    public val accessModes: List<String>,
    public val allocatedResourceStatuses: RawJsonObject,
    public val allocatedResources: StringOrNumber,
    public val capacity: StringOrNumber,
    public val conditions: List<PersistentVolumeClaimCondition>,
    public val currentVolumeAttributesClassName: String,
    public val modifyVolumeStatus: ModifyVolumeStatus,
    public val phase: String,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "PersistentVolumeClaimStatus"
}
