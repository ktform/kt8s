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
class ImageProbesBuilder {
  data class Probes(
    val liveness: Option<String> = none(),
    val readiness: Option<String> = none(),
    val startup: Option<String> = none(),
  )

  private var livenessProbe: Option<String> = none()
  private var readinessProbe: Option<String> = none()
  private var startupProbe: Option<String> = none()

  fun liveness(command: String) {
    livenessProbe = command.some()
  }

  fun readiness(command: String) {
    readinessProbe = command.some()
  }

  fun startup(command: String) {
    startupProbe = command.some()
  }

  internal fun build(): Probes = Probes(
    liveness = livenessProbe,
    readiness = readinessProbe,
    startup = startupProbe,
  )
}
