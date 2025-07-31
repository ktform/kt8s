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

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = KubernetesMicroTimeSerializer::class)
data class KubernetesMicroTime(val dateTime: LocalDateTime) {

  companion object {
    // RFC3339 microsecond format: "2006-01-02T15:04:05.000000Z07:00"
    internal val RFC3339_MICRO_FORMAT = LocalDateTime.Format {
      date(LocalDate.Formats.ISO)
      char('T')
      time(
        LocalTime.Format {
        hour()
        char(':')
        minute()
        char(':')
        second()
        char('.')
        secondFraction(6) // microseconds
      })
      char('Z')
    }
  }

  override fun toString(): String {
    return dateTime.format(RFC3339_MICRO_FORMAT)
  }
}

object KubernetesMicroTimeSerializer : KSerializer<KubernetesMicroTime> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("KubernetesMicroTime", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: KubernetesMicroTime) {
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): KubernetesMicroTime {
    val dateTimeString = decoder.decodeString()
    return try {
      val dateTime = LocalDateTime.parse(dateTimeString, KubernetesMicroTime.RFC3339_MICRO_FORMAT)
      KubernetesMicroTime(dateTime)
    } catch (e: Exception) {
      throw SerializationException("Failed to parse KubernetesMicroTime from '$dateTimeString'", e)
    }
  }
}