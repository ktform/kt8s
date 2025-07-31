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
class ImageEndpointsBuilder {
  data class Endpoints(
    val healthcheck: Option<String> = none(),
    val metrics: Option<String> = none(),
    val service: Option<String> = none(),
  )

  private var healthcheckUrl: Option<String> = none()
  private var metricsUrl: Option<String> = none()
  private var serviceUrl: Option<String> = none()

  fun healthcheck(url: String) {
    healthcheckUrl = url.some()
  }

  fun metrics(url: String) {
    metricsUrl = url.some()
  }

  fun service(url: String) {
    serviceUrl = url.some()
  }

  internal fun build(): Endpoints = Endpoints(
    healthcheck = healthcheckUrl,
    metrics = metricsUrl,
    service = serviceUrl,
  )
}