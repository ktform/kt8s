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

package dev.ktform.kt8s.container.github

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import dev.ktform.kt8s.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class GithubClient {
  fun getOwnerRepoFrom(url: String): Pair<String, String> =
    url.replace("git@github.com:", "")
      .replace("https://github.com/", "")
      .split("/")
      .takeIf { it.size == 2 }
      ?.let { it[0] to it[1].removeSuffix(".git") }
      ?: ("" to "")

  @Serializable
  data class GitObject(
    val sha: String,
    val type: String,
    val url: String,
  )

  @Serializable
  data class GitTagsResponse(
    val ref: String,
    @SerialName("node_id")
    val nodeId: String,
    val url: String,
    @SerialName("object")
    val gitObject: GitObject,
  )

  @Serializable
  data class GitReleaseResponse(
    val id: Long,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("tag_name")
    val tagName: String,
    val name: String?,
    val body: String?,
    val draft: Boolean,
    val prerelease: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("published_at")
    val publishedAt: String?,
  )

  private fun mapError(throwable: Throwable): String = when (throwable) {
    is kotlinx.serialization.SerializationException -> "Failed to parse GitHub API response: ${throwable.message}"
    else -> "Network request failed: ${throwable.message}"
  }

  private suspend inline fun <reified T> fetchPage(
    endpoint: String,
    owner: String,
    repo: String,
    page: Int,
    authToken: String?,
    client: io.ktor.client.HttpClient,
  ): Either<String, List<T>> = Either.catch {
    val response = client.request("https://api.github.com/repos/$owner/$repo/$endpoint") {
      method = io.ktor.http.HttpMethod.Get
      header("X-GitHub-Api-Version", API_VERSION)
      header("Accept", "application/vnd.github.v3+json")
      authToken?.let { token ->
        header("Authorization", "Bearer $token")
      }
      parameter("page", page)
      parameter("per_page", 100)
    }

    if (response.status.value == 200) {
      response.body<List<T>>()
    } else {
      return "HTTP ${response.status.value}: ${response.status.description}".left()
    }
  }.mapLeft(::mapError)

  private suspend fun fetchTagsPage(
    owner: String,
    repo: String,
    page: Int,
    authToken: String?,
    client: io.ktor.client.HttpClient,
  ): Either<String, List<GitTagsResponse>> =
    fetchPage("git/refs/tags", owner, repo, page, authToken, client)

  private suspend fun fetchReleasesPage(
    owner: String,
    repo: String,
    page: Int,
    authToken: String?,
    client: io.ktor.client.HttpClient,
  ): Either<String, List<GitReleaseResponse>> =
    fetchPage("releases", owner, repo, page, authToken, client)

  private suspend fun <T> fetchAllPages(
    owner: String,
    repo: String,
    authToken: String?,
    client: io.ktor.client.HttpClient,
    page: Int = 1,
    accumulator: List<T> = emptyList(),
    getter: suspend (
      owner: String,
      repo: String,
      page: Int,
      authToken: String?,
      client: io.ktor.client.HttpClient,
    ) -> Either<String, List<T>>,
  ): Either<String, List<T>> =
    getter(owner, repo, page, authToken, client).flatMap { data ->
      val newAccumulator = accumulator + data
      when {
        data.isEmpty() || data.size < 100 -> newAccumulator.right()
        else -> fetchAllPages(owner, repo, authToken, client, page + 1, newAccumulator, getter)
      }
    }

  private suspend fun <T> fetchAllFromRepo(
    url: String,
    authToken: String?,
    getter: suspend (String, String, Int, String?, io.ktor.client.HttpClient) -> Either<String, List<T>>,
    transform: (T) -> String,
  ): Either<String, List<String>> =
    getOwnerRepoFrom(url).let { (owner, repo) ->
      when {
        owner.isBlank() || repo.isBlank() -> "Invalid URL: $url".left()
        else -> {
          val client = HttpClient.getInstance()
          fetchAllPages(owner, repo, authToken, client, getter = getter)
            .map { items -> items.map(transform) }
        }
      }
    }

  suspend fun getTags(url: String, authToken: String? = getGithubToken()): Either<String, List<String>> =
    fetchAllFromRepo(url, authToken, ::fetchTagsPage) { it.ref.removePrefix("refs/tags/") }

  suspend fun getReleases(url: String, authToken: String? = getGithubToken()): Either<String, List<String>> =
    fetchAllFromRepo(url, authToken, ::fetchReleasesPage) { it.tagName }

  companion object {
    const val API_VERSION = "2022-11-28"
  }
}