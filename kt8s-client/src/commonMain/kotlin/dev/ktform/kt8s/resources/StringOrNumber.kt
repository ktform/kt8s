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

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull


@Serializable(with = StringOrNumberSerializer::class)
data class StringOrNumber(
  val value: Either<String, Double>,
) {
  fun isString(): Boolean = value.isLeft()
  fun isNumber(): Boolean = value.isRight()

  fun asString(): String = value.fold({ it }, { throw IllegalStateException("Not a string") })
  fun asStringOrNull(): String? = value.fold({ it }, { null })
  fun asStringOrElse(block: () -> String): String = value.fold({ it }, { block() })

  fun asNumber(): Double = value.fold({ throw IllegalStateException("Not a number") }, { it })
  fun asNumberOrNull(): Double? = value.fold({ null }, { it })
  fun asNumberOrElse(block: () -> Double): Double = value.fold({ block() }, { it })
}

object StringOrNumberSerializer : KSerializer<StringOrNumber> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("StringOrNumber", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: StringOrNumber) {
    value.value.fold(
      { encoder.encodeString(it) },
      { encoder.encodeDouble(it) },
    )
  }

  override fun deserialize(decoder: Decoder): StringOrNumber {
    return when (decoder) {
      is JsonDecoder -> {
        when (val element = decoder.decodeJsonElement()) {
          is JsonPrimitive -> {
            element.doubleOrNull?.let { StringOrNumber(it.right()) }
              ?: StringOrNumber(element.content.left())
          }

          else -> throw SerializationException("Expected JsonPrimitive but got ${element::class}")
        }
      }

      else -> {
        try {
          StringOrNumber(decoder.decodeDouble().right())
        } catch (e: Exception) {
          StringOrNumber(decoder.decodeString().left())
        }
      }
    }
  }
}
