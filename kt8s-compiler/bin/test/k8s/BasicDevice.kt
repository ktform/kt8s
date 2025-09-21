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
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * @param allNodes AllNodes indicates that all nodes have access to the device.
 *
 * Must only be set if Spec.PerDeviceNodeSelection is set to true. At most one of NodeName,
 * NodeSelector and AllNodes can be set.
 *
 * @param attributes Attributes defines the set of attributes for this device. The name of each
 *   attribute must be unique in that set.
 *
 * The maximum number of attributes and capacities combined is 32.
 *
 * @param capacity Capacity defines the set of capacities for this device. The name of each capacity
 *   must be unique in that set.
 *
 * The maximum number of attributes and capacities combined is 32.
 *
 * @param consumesCounters ConsumesCounters defines a list of references to sharedCounters and the
 *   set of counters that the device will consume from those counter sets.
 *
 * There can only be a single entry per counterSet.
 *
 * The total number of device counter consumption entries must be <= 32. In addition, the total
 * number in the entire ResourceSlice must be <= 1024 (for example, 64 devices with 16 counters
 * each).
 *
 * @param nodeName NodeName identifies the node where the device is available.
 *
 * Must only be set if Spec.PerDeviceNodeSelection is set to true. At most one of NodeName,
 * NodeSelector and AllNodes can be set.
 *
 * @param nodeSelector NodeSelector defines the nodes where the device is available.
 *
 * Must only be set if Spec.PerDeviceNodeSelection is set to true. At most one of NodeName,
 * NodeSelector and AllNodes can be set.
 *
 * @param taints If specified, these are the driver-defined taints.
 *
 * The maximum number of taints is 4.
 *
 * This is an alpha field and requires enabling the DRADeviceTaints feature gate.
 */
@Serializable
public data class BasicDevice(
    public val allNodes: Boolean,
    public val attributes: DeviceAttribute,
    public val capacity: StringOrNumber,
    public val consumesCounters: List<DeviceCounterConsumption>,
    public val nodeName: String,
    public val nodeSelector: NodeSelector,
    public val taints: List<DeviceTaint>,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.resource/v1alpha3"

    @Transient override val group: String = "io.k8s.api.resource"

    @Transient override val version: String = "v1alpha3"

    @SerialName("kind") override val kind: String = "BasicDevice"
}
