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
import arrow.core.left
import arrow.core.right
import dev.ktform.kt8s.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class GithubClient {

    /**
     * Extracts owner and repository name from a given URL.
     *
     * Accepts URLs in the following formats:
     * - `git@github.com:owner/repo.git`
     * - `https://github.com/owner/repo.git`
     * - `https://github.com/owner/repo`
     *
     * Returns a pair of owner and repository names. If the URL is invalid, returns an empty pair.
     *
     * @param url the URL to parse
     * @return a pair of owner and repository names
     */
    fun getOwnerRepoFrom(url: String): Pair<String, String> =
        url.replace("git@github.com:", "")
            .replace("https://github.com/", "")
            .split("/")
            .takeIf { it.size == 2 }
            ?.let { it[0] to it[1].removeSuffix(".git") } ?: ("" to "")

    @Serializable data class GitObject(val sha: String, val type: String, val url: String)

    @Serializable
    data class GitTagsResponse(
        val ref: String,
        @SerialName("node_id") val nodeId: String,
        val url: String,
        @SerialName("object") val gitObject: GitObject,
    )

    @Serializable
    data class GitReleaseResponse(
        val id: Long,
        @SerialName("node_id") val nodeId: String,
        @SerialName("tag_name") val tagName: String,
        val name: String?,
        val body: String?,
        val draft: Boolean,
        val prerelease: Boolean,
        @SerialName("created_at") val createdAt: String,
        @SerialName("published_at") val publishedAt: String?,
    )

    private fun mapError(throwable: Throwable): String =
        when (throwable) {
            is kotlinx.serialization.SerializationException ->
                "Failed to parse GitHub API response: ${throwable.message}"

            else -> "Network request failed: ${throwable.message}"
        }

    private data class PageResult<T>(val items: List<T>, val nextUrl: String?)

    private fun parseNextLink(linkHeader: String?): String? {
        if (linkHeader.isNullOrBlank()) return null
        return linkHeader
            .split(',')
            .map { it.trim() }
            .firstOrNull { it.contains("rel=\"next\"") }
            ?.let { segment ->
                val start = segment.indexOf('<')
                val end = segment.indexOf('>')
                if (start in 0..<end) segment.substring(start + 1, end) else null
            }
    }

    private suspend inline fun <reified T> fetchPageByUrl(
        url: String,
        authToken: String?,
        client: io.ktor.client.HttpClient,
    ): Either<String, PageResult<T>> =
        Either.catch {
                val response =
                    client.request(url) {
                        method = io.ktor.http.HttpMethod.Get
                        header("X-GitHub-Api-Version", API_VERSION)
                        header("Accept", "application/vnd.github.v3+json")
                        authToken?.let { token -> header("Authorization", "Bearer $token") }
                    }

                if (response.status.value == 200) {
                    val items = response.body<List<T>>()
                    val next = parseNextLink(response.headers["Link"])
                    PageResult(items, next)
                } else {
                    return "HTTP ${response.status.value}: ${response.status.description}".left()
                }
            }
            .mapLeft(::mapError)

    private suspend inline fun <reified T> fetchAll(
        owner: String,
        repo: String,
        endpoint: String,
        authToken: String?,
        client: io.ktor.client.HttpClient,
        limit: Int? = null,
    ): Either<String, List<T>> {
        if (limit != null && limit <= 0) return emptyList<T>().right()

        var url =
            "https://api.github.com/repos/$owner/$repo/$endpoint?per_page=1000&sort=created&direction=desc"
        val acc = mutableListOf<T>()

        while (true) {
            val remaining = limit?.let { it - acc.size } ?: Int.MAX_VALUE
            if (remaining <= 0) break

            when (val page = fetchPageByUrl<T>(url, authToken, client)) {
                is Either.Left -> return page
                is Either.Right -> {
                    val items = page.value.items
                    if (items.isEmpty()) break
                    val toAdd = if (remaining < items.size) items.take(remaining) else items
                    acc.addAll(toAdd)
                    val next = page.value.nextUrl
                    if (next == null) break else url = next
                }
            }
        }

        return acc.toList().right()
    }

    private suspend fun <T> fetchAllFromRepo(
        url: String,
        authToken: String?,
        getter:
            suspend (
                owner: String,
                repo: String,
                authToken: String?,
                client: io.ktor.client.HttpClient,
                limit: Int?,
            ) -> Either<String, List<T>>,
        limit: Int? = null,
        transform: (T) -> String,
    ): Either<String, List<String>> =
        getOwnerRepoFrom(url).let { (owner, repo) ->
            when {
                owner.isBlank() || repo.isBlank() -> "Invalid URL: $url".left()
                else -> {
                    val client = HttpClient.invoke()
                    getter(owner, repo, authToken, client, limit).map { items ->
                        val mapped = items.map(transform).sortedDescending()
                        limit?.let { mapped.take(it) } ?: mapped
                    }
                }
            }
        }

    /**
     * Fetches all tags from a given repository URL.
     *
     * @param url repository URL
     * @param authToken optional GitHub authentication token
     * @param limit optional limit on the number of tags to fetch
     * @return Either containing error message or list of tag names
     */
    suspend fun getTags(
        url: String,
        authToken: String? = getGithubToken(),
        limit: Int? = null,
    ): Either<String, List<String>> =
        fetchAllFromRepo(
            url,
            authToken,
            getter = { owner, repo, token, client, lim ->
                fetchAll<GitTagsResponse>(owner, repo, "git/refs/tags", token, client, lim)
            },
            limit = limit,
            transform = { it.ref.removePrefix("refs/tags/") },
        )

    /**
     * Fetches all releases for a given repository URL.
     *
     * @param url repository URL
     * @param authToken optional GitHub authentication token
     * @param limit optional limit on the number of releases to fetch
     * @return Either containing error message or list of release tags
     */
    suspend fun getReleases(
        url: String,
        authToken: String? = getGithubToken(),
        limit: Int? = null,
    ): Either<String, List<String>> =
        fetchAllFromRepo(
            url,
            authToken,
            getter = { owner, repo, token, client, lim ->
                fetchAll<GitReleaseResponse>(owner, repo, "releases", token, client, lim)
            },
            limit = limit,
            transform = { it.tagName },
        )

    companion object {
        const val API_VERSION = "2022-11-28"
    }
}
