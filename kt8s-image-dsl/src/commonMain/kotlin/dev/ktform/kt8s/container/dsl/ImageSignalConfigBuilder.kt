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

package dev.ktform.kt8s.container.dsl

import arrow.core.Option
import arrow.core.none
import arrow.core.some

@ImageDsl
class ImageSignalConfigBuilder {
  data class Signals(
    val stopGracefully: Option<dev.ktform.kt8s.container.Signal> = none(),
    val stopImmediately: Option<dev.ktform.kt8s.container.Signal> = none(),
    val reloadConfig: Option<dev.ktform.kt8s.container.Signal> = none(),
  )

  private var stopGracefully: Option<dev.ktform.kt8s.container.Signal> = none()
  private var stopImmediately: Option<dev.ktform.kt8s.container.Signal> = none()
  private var reloadConfig: Option<dev.ktform.kt8s.container.Signal> = none()

  fun stopGracefully(signal: dev.ktform.kt8s.container.Signal) {
    stopGracefully = signal.some()
  }

  fun stopImmediately(signal: dev.ktform.kt8s.container.Signal) {
    stopImmediately = signal.some()
  }

  fun reloadConfig(signal: dev.ktform.kt8s.container.Signal) {
    reloadConfig = signal.some()
  }

  internal fun build(): Signals = Signals(
    stopGracefully = stopGracefully,
    stopImmediately = stopImmediately,
    reloadConfig = reloadConfig,
  )
}
