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
package dev.ktform.kt8s.container

import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.readByteArray

fun String.toBuffer() = Buffer().apply { write(this@toBuffer.toByteArray()) }

fun Source.readString() = this.readByteArray().toString()

fun Sink.writeString(string: String, charset: Charset = Charsets.UTF_8) =
    this.write(string.toByteArray(charset))

fun List<String>.joinCommands(prefix: String = Distro.ident) =
    joinToString(" ; \\\n") { "$prefix$it" }
