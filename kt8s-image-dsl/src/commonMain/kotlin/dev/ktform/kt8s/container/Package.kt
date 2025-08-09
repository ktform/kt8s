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

import arrow.core.*
import dev.ktform.kt8s.container.github.GithubClient
import dev.ktform.kt8s.container.packages.languages.*
import io.github.z4kn4fein.semver.Version
import io.github.z4kn4fein.semver.toVersion

data class Package(
  val packageName: String,
  val repo: String,
  val runtime: Boolean = false,
  val providers: NonEmptyList<Environment.Provider> = Environment.Provider.all.toNonEmptyListOrThrow(),

  val buildDependencies: Map<Distro, Set<String>> = emptyMap(),
  val runDependencies: Map<Distro, Set<String>> = emptyMap(),

  val buildPackageDependencies: Map<Distro, Set<Package>> = emptyMap(),
  val runPackageDependencies: Map<Distro, Set<Package>> = emptyMap(),

  val flavours: Set<Package> = emptySet(),
  val defaultFlavour: Option<Package> = none(),

  val buildCommands: (Environment, List<Package>) -> List<String> = { _, _ -> emptyList() },
  val distrolessCommands: (Environment, List<Package>) -> List<String> = { _, _ -> emptyList() },

  val repoVersion: (String, toRepo: Boolean) -> String = withVPrefix,
  val availableVersions: suspend (Environment) -> Either<String, List<String>> = { _ ->
    val client = GithubClient()
    client.getTags(repo)
      .map { all ->
        all.map { it.removePrefix("v").trim() }
          .mapNotNull { s -> Either.catch { s.toVersion() }.getOrNull() }
          .filter { v -> !v.isPreRelease && (v.major == 0 || v.isStable) }
          .sortedDescending()
          .map(Version::toString)
          .toList()
      }
  },
  val stopGracefullySignal: Signal = defaultStopGracefullySignal,
  val stopImmediatelySignal: Signal = defaultStopImmediatelySignal,
  val reloadConfigSignal: Signal = defaultReloadConfigSignal,
) : Renderable {

  override suspend fun render(version: String, env: Environment): Either<String, String> = buildString {
    ""
  }.right()

  override suspend fun versions(env: Environment): Either<String, List<String>> = availableVersions.invoke(env)

  override suspend fun render(): Either<String, String> = latestVersion().flatMap { render(it, Environment.default) }

  override suspend fun versions(): Either<String, List<String>> = availableVersions.invoke(Environment.default)

  companion object {
    val defaultStopGracefullySignal = Signal.SIGTERM
    val defaultStopImmediatelySignal = Signal.SIGINT
    val defaultReloadConfigSignal = Signal.SIGHUP

    val asIs: (String, toRepo: Boolean) -> String = { version, _ -> version }

    val withVPrefix: (String, toRepo: Boolean) -> String = { version, toRepo ->
      if (toRepo) {
        "v$version"
      } else {
        version
      }
    }

    val all: Map<String, Package> = mapOf(
      // Languages
      "deno" to Deno.`package`,
      "dotnet" to DotNet.`package`,
      "golang" to Golang.`package`,
      "go" to Golang.`package`,
      "jdk" to JavaJre.`package`,
      "jre" to JavaJdk.`package`,
      "nodejs" to NodeJS.`package`,
      "python" to Python.`package`,
      "ruby" to Ruby.`package`,
      "rust" to Rust.`package`,

//      // CLI Tools
//      "argo" to Argo.`package`,
//      "awscli" to AwsCli.`package`,
//      "aws-cli" to AwsCli.`package`,
//      "bazel" to Bazel.`package`,
//      "cilium" to Cilium.`package`,
//      "cmake" to Cmake.`package`,
//      "cosign" to Cosign.`package`,
//      "doctl" to DoCtl.`package`,
//      "firebase" to Firebase.`package`,
//      "gcloud" to GCloud.`package`,
//      "gradle" to Gradle.`package`,
//      "grype" to Grype.`package`,
//      "helm" to Helm.`package`,
//      "k9s" to K9s.`package`,
//      "kind" to Kind.`package`,
//      "kubectl" to KubeCtl.`package`,
//      "minikube" to Minikube.`package`,
//      "opentofu" to OpenTofu.`package`,
//      "pipx" to PipX.`package`,
//      "podman" to Podman.`package`,
//      "protoc" to Protoc.`package`,
//      "scala" to ScalaSbt.`package`,
//      "sbt" to ScalaSbt.`package`,
//      "supabase" to Supabase.`package`,
//      "syft" to Syft.`package`,
//      "terraform" to Terraform.`package`,
//      "trivy" to Trivy.`package`,
//      "uv" to UV.`package`,
    )
  }
}
