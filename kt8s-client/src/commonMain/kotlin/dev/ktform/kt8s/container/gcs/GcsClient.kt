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
package dev.ktform.kt8s.container.gcs

import arrow.core.Either
import dev.ktform.kt8s.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class GcsListItem(@SerialName("name") val name: String)

@Serializable
data class GcsListResponse(
    @SerialName("items") val items: List<GcsListItem> = emptyList(),
    @SerialName("nextPageToken") val nextPageToken: String? = null,
)

class GcsClient {
    private val client = HttpClient()

    suspend fun getVersions(bucket: String): Either<String, List<String>> =
        Either.catch {
                val allItems = mutableListOf<String>()
                var pageToken: String? = null

                do {
                    val url =
                        if (pageToken == null) {
                            "https://storage.googleapis.com/storage/v1/b/$bucket/o?delimiter=/"
                        } else {
                            "https://storage.googleapis.com/storage/v1/b/$bucket/o?delimiter=/&pageToken=$pageToken"
                        }
                    val response: GcsListResponse = client.get(url).body()

                    allItems.addAll(response.items.map { it.name })
                    pageToken = response.nextPageToken
                } while (pageToken != null)

                allItems
            }
            .mapLeft { it.message ?: "Unknown error" }
}
