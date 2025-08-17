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
package dev.ktform.kt8s

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class KubernetesClient(private val apiServerUrl: String, private val bearerToken: String? = null) {
    private val httpClient = HttpClient()

    suspend fun listPods(namespace: String = "default"): String {
        val url = "$apiServerUrl/api/v1/namespaces/$namespace/pods"
        val response: HttpResponse =
            httpClient.get(url) {
                bearerToken?.let { header(HttpHeaders.Authorization, "Bearer $it") }
                accept(ContentType.Application.Json)
            }
        return response.bodyAsText()
    }
}
