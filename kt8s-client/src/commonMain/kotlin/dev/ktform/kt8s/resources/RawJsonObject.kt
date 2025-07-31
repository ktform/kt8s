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

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject

@Serializable(with = RawJsonObjectSerializer::class)
data class RawJsonObject(
  val json: JsonObject,
)

object RawJsonObjectSerializer : KSerializer<RawJsonObject> {
  override val descriptor: SerialDescriptor = JsonObject.serializer().descriptor

  override fun serialize(encoder: Encoder, value: RawJsonObject) {
    JsonObject.serializer().serialize(encoder, value.json)
  }

  override fun deserialize(decoder: Decoder): RawJsonObject {
    val jsonObject = JsonObject.serializer().deserialize(decoder)
    return RawJsonObject(jsonObject)
  }
}