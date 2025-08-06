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
import dev.ktform.kt8s.container.packages.*
import dev.ktform.kt8s.container.packages.languages.*

data class Package(
  val packageName: String,
  val runtime: Boolean = false,
  val providers: NonEmptyList<Environment.Provider> = Environment.Provider.all.toNonEmptyListOrThrow(),
  val build: Map<Distro, NonEmptyList<String>> = emptyMap(),
  val distroless: Map<Distro, NonEmptyList<String>> = emptyMap(),
  val buildDependencies: Map<Distro, NonEmptyList<Package>> = emptyMap(),
  val dependencies: Map<Distro, NonEmptyList<Package>> = emptyMap(),
  val flavours: List<Package> = emptyList(),
  val defaultFlavour: Option<Package> = none(),
  val buildCommand: (Environment, List<Package>) -> String = { _, _ -> "" },
  val buildDistroless: (Environment, List<Package>) -> String = { _, _ -> "" },
  val repo: Option<String> = none(),
  val repoVersion: Option<String> = none(),
  val repoVersionFilter: (String) -> String = { it },
  val availableVersions: (Environment) -> List<String> = { emptyList() },
) : Renderable {

  override fun latestVersion(env: Environment): String =
    availableVersions(env)
      .maxByOrNull { it }
      .toOption()
      .getOrElse { throw Exception("Unable to determine $packageName latest version") }

  override fun render(version: String, env: Environment): String = buildString {
    ""
  }

  override fun versions(env: Environment): List<String> = availableVersions.invoke(env)

  companion object {
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

      // CLI Tools
      "argo" to Argo.`package`,
      "awscli" to AwsCli.`package`,
      "aws-cli" to AwsCli.`package`,
      "bazel" to Bazel.`package`,
      "cilium" to Cilium.`package`,
      "cmake" to Cmake.`package`,
      "cosign" to Cosign.`package`,
      "doctl" to DoCtl.`package`,
      "firebase" to Firebase.`package`,
      "gcloud" to GCloud.`package`,
      "gradle" to Gradle.`package`,
      "grype" to Grype.`package`,
      "helm" to Helm.`package`,
      "k9s" to K9s.`package`,
      "kind" to Kind.`package`,
      "kubectl" to KubeCtl.`package`,
      "minikube" to Minikube.`package`,
      "opentofu" to OpenTofu.`package`,
      "pipx" to PipX.`package`,
      "podman" to Podman.`package`,
      "protoc" to Protoc.`package`,
      "scala" to ScalaSbt.`package`,
      "sbt" to ScalaSbt.`package`,
      "supabase" to Supabase.`package`,
      "syft" to Syft.`package`,
      "terraform" to Terraform.`package`,
      "trivy" to Trivy.`package`,
      "uv" to UV.`package`,
    )
  }
}
