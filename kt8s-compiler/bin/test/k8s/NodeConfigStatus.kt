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
 * @param active Active reports the checkpointed config the node is actively using. Active will
 *   represent either the current version of the Assigned config, or the current LastKnownGood
 *   config, depending on whether attempting to use the Assigned config results in an error.
 * @param assigned Assigned reports the checkpointed config the node will try to use. When
 *   Node.Spec.ConfigSource is updated, the node checkpoints the associated config payload to local
 *   disk, along with a record indicating intended config. The node refers to this record to choose
 *   its config checkpoint, and reports this record in Assigned. Assigned only updates in the status
 *   after the record has been checkpointed to disk. When the Kubelet is restarted, it tries to make
 *   the Assigned config the Active config by loading and validating the checkpointed payload
 *   identified by Assigned.
 * @param error Error describes any problems reconciling the Spec.ConfigSource to the Active config.
 *   Errors may occur, for example, attempting to checkpoint Spec.ConfigSource to the local Assigned
 *   record, attempting to checkpoint the payload associated with Spec.ConfigSource, attempting to
 *   load or validate the Assigned config, etc. Errors may occur at different points while syncing
 *   config. Earlier errors (e.g. download or checkpointing errors) will not result in a rollback to
 *   LastKnownGood, and may resolve across Kubelet retries. Later errors (e.g. loading or validating
 *   a checkpointed config) will result in a rollback to LastKnownGood. In the latter case, it is
 *   usually possible to resolve the error by fixing the config assigned in Spec.ConfigSource. You
 *   can find additional information for debugging by searching the error message in the Kubelet
 *   log. Error is a human-readable description of the error state; machines can check whether or
 *   not Error is empty, but should not rely on the stability of the Error text across Kubelet
 *   versions.
 * @param lastKnownGood LastKnownGood reports the checkpointed config the node will fall back to
 *   when it encounters an error attempting to use the Assigned config. The Assigned config becomes
 *   the LastKnownGood config when the node determines that the Assigned config is stable and
 *   correct. This is currently implemented as a 10-minute soak period starting when the local
 *   record of Assigned config is updated. If the Assigned config is Active at the end of this
 *   period, it becomes the LastKnownGood. Note that if Spec.ConfigSource is reset to nil (use local
 *   defaults), the LastKnownGood is also immediately reset to nil, because the local default config
 *   is always assumed good. You should not make assumptions about the node's method of determining
 *   config stability and correctness, as this may change or become configurable in the future.
 */
@Serializable
public data class NodeConfigStatus(
    public val active: NodeConfigSource,
    public val assigned: NodeConfigSource,
    public val error: String,
    public val lastKnownGood: NodeConfigSource,
) : Resource {
    @SerialName("apiVersion") override val apiVersion: String = "io.k8s.api.core/v1"

    @Transient override val group: String = "io.k8s.api.core"

    @Transient override val version: String = "v1"

    @SerialName("kind") override val kind: String = "NodeConfigStatus"
}
