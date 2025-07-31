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
import kotlinx.serialization.json.intOrNull

@Serializable(with = IntOrStringSerializer::class)
data class IntOrString(
  val value: Either<Int, String>,
) {
  fun isInt(): Boolean = value.isLeft()
  fun isString(): Boolean = value.isRight()

  fun asInt(): Int = value.fold({ it }, { throw IllegalStateException("Not an int") })
  fun asIntOrNull(): Int? = value.fold({ it }, { null })
  fun asIntOrElse(block: () -> Int): Int = value.fold({ it }, { block() })

  fun asString(): String = value.fold({ throw IllegalStateException("Not a string") }, { it })
  fun asStringOrNull(): String? = value.fold({ null }, { it })
  fun asStringOrElse(block: () -> String): String = value.fold({ block() }, { it })
}

object IntOrStringSerializer : KSerializer<IntOrString> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("IntOrString", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: IntOrString) {
    value.value.fold(
      { encoder.encodeInt(it) },
      { encoder.encodeString(it) },
    )
  }

  override fun deserialize(decoder: Decoder): IntOrString {
    return when (decoder) {
      is JsonDecoder -> {
        when (val element = decoder.decodeJsonElement()) {
          is JsonPrimitive -> {
            element.intOrNull?.let { IntOrString(it.left()) }
              ?: IntOrString(element.content.right())
          }

          else -> throw SerializationException("Expected JsonPrimitive but got ${element::class}")
        }
      }

      else -> {
        try {
          IntOrString(decoder.decodeInt().left())
        } catch (e: Exception) {
          IntOrString(decoder.decodeString().right())
        }
      }
    }
  }
}