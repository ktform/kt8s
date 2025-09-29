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
package dev.ktform.kt8s.container.components

import arrow.core.NonEmptySet
import arrow.core.Option
import arrow.core.nonEmptySetOf
import arrow.core.toOption
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.Provider
import dev.ktform.kt8s.container.versions.Versions
import io.ktor.util.*

sealed interface Component<T : Versions<T>> {
    val name: String
    val applicableFlavours: Set<Component<*>>
    val applicableProviders: Set<Provider>
        get() = Provider.all

    val appliedVersions: Versions<T>

    fun image(env: Environment): String

    companion object {
        val defaultFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(
                JavaComponent.OpenJDK,
                PythonComponent.CPython,
                RubyComponent.Ruby,
                RustComponent.Nightly,
            )
        }

        val buildNative: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(CmakeComponent.Cmake, ProtocComponent.Protoc)
        }
        val buildJvm: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(BazelComponent.Bazel, BazelComponent.Bazelisk, GradleComponent.Gradle)
        }
        val buildPython: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(UVComponent.UV, PipXComponent.PipX)
        }

        val cloudManagementCli: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(
                GCloudComponent.GCloud,
                AwsCliComponent.AwsCli,
                OpenTofuComponent.OpenTofu,
                DoCtlComponent.DoCtl,
                FirebaseComponent.Firebase,
                KubeCtlComponent.KubeCtl,
                K9sComponent.K9s,
                TektonComponent.TektonCli,
                KindComponent.Kind,
                PodmanComponent.Podman,
                MinikubeComponent.Minikube,
                OpenTofuComponent.OpenTofu,
            )
        }
        val containerSecurityCli: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(
                TrivyComponent.Trivy,
                GrypeComponent.Grype,
                SyftComponent.Syft,
                CosignComponent.Cosign,
            )
        }

        val golangFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(GolangComponent.Golang)
        }

        val javaFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(JavaComponent.OpenJDK, JavaComponent.GraalVM, JavaComponent.OpenJ9)
        }

        val pythonFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(PythonComponent.CPython, PythonComponent.PyPy)
        }

        val dotNetFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(DotNetComponent.DotNet)
        }

        val denoFlavours: NonEmptySet<Component<*>> by lazy { nonEmptySetOf(DenoComponent.Deno) }

        val rubyFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(RubyComponent.Ruby, RubyComponent.MRuby)
        }

        val rustFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(RustComponent.Stable, RustComponent.Nightly)
        }

        val nodeFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(NodeJsComponent.NodeJs)
        }

        val postgresFlavours: NonEmptySet<Component<*>> by lazy {
            nonEmptySetOf(PostgreSQLComponent.CNPG, PostgreSQLComponent.Stackgres)
        }

        val allSelectableFlavours: NonEmptySet<Component<*>> by lazy {
            javaFlavours + pythonFlavours + rubyFlavours + rustFlavours + postgresFlavours
        }

        fun conflictingFlavours(proposedFlavours: NonEmptySet<Component<*>>): List<Component<*>> =
            listOf(javaFlavours, pythonFlavours, rubyFlavours, rustFlavours, postgresFlavours).fold(
                emptyList()
            ) { acc, flavourGroup ->
                val conflictsInGroup = proposedFlavours.filter { it in flavourGroup }
                if (conflictsInGroup.size > 1) acc + conflictsInGroup else acc
            }

        fun flavourByName(name: String): Option<Component<*>> =
            allSelectableFlavours
                .find {
                    it.name.toLowerCasePreservingASCIIRules() ==
                        name.toLowerCasePreservingASCIIRules()
                }
                .toOption()
    }
}
