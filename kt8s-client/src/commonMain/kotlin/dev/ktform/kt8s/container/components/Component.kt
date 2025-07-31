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

import arrow.core.NonEmptyList
import arrow.core.Option
import arrow.core.nonEmptyListOf
import arrow.core.toOption
import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.container.Provider
import dev.ktform.kt8s.container.packages.Firebase
import dev.ktform.kt8s.container.packages.GCloud
import dev.ktform.kt8s.container.packages.Grype
import dev.ktform.kt8s.container.packages.OpenTofu
import dev.ktform.kt8s.container.packages.UV
import dev.ktform.kt8s.container.versions.Versions
import io.ktor.util.*

sealed interface Component<T : Versions<T>> {
    val name: String
    val applicableFlavours: List<Component<*>>
    val applicableProviders: List<Provider>
      get() = Provider.all

    val charts: List<Chart<T>>
      get() = emptyList()

    val cliTools: List<Component<*>>
      get() = emptyList()

    companion object {
        val defaultFlavours: NonEmptyList<Component<*>> =
            nonEmptyListOf(
                JavaComponent.OpenJDK,
                PythonComponent.CPython,
                RubyComponent.Ruby,
                RustComponent.Nightly,
            )

        object Common {
          val base: NonEmptyList<Component<*>> = nonEmptyListOf(BaseComponent.Base)
          val baseBuilder: NonEmptyList<Component<*>> = nonEmptyListOf(BaseBuilderComponent.BaseBuilder)
          val baseDevelopment: NonEmptyList<Component<*>> = nonEmptyListOf(BaseDevelopmentComponent.BaseDevelopment)

          val buildNative: NonEmptyList<Component<*>> = nonEmptyListOf(CmakeComponent.Cmake)
          val buildJvm: NonEmptyList<Component<*>> = nonEmptyListOf(BazelComponent.Bazel, BazelComponent.Bazelisk, GradleComponent.Gradle)
          val buildPython: NonEmptyList<Component<*>> = nonEmptyListOf(UVComponent.UV)

          val cloudManagementCli: NonEmptyList<Component<*>> = nonEmptyListOf(GCloudComponent.GCloud, AwsCliComponent.AwsCli,
            TerraformComponent.Terraform, OpenTofuComponent.OpenTofu, DoCtlComponent.DoCtl, FirebaseComponent.Firebase)
          val containerSecurityCli: NonEmptyList<Component<*>> = nonEmptyListOf(TrivyComponent.Trivy, GrypeComponent.Grype, SyftComponent.Syft)
        }

        val golangFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(GolangComponent.Golang)

        val javaFlavours: NonEmptyList<Component<*>> =
            nonEmptyListOf(JavaComponent.OpenJDK, JavaComponent.GraalVM, JavaComponent.OpenJ9)

        val pythonFlavours: NonEmptyList<Component<*>> =
            nonEmptyListOf(PythonComponent.CPython, PythonComponent.PyPy)

        val dotNetFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(DotNetComponent.DotNet)

        val rubyFlavours: NonEmptyList<Component<*>> =
            nonEmptyListOf(RubyComponent.Ruby, RubyComponent.MRuby)

        val rustFlavours: NonEmptyList<Component<*>> =
            nonEmptyListOf(RustComponent.Stable, RustComponent.Nightly)

        val postgresFlavours: NonEmptyList<Component<*>> =
            nonEmptyListOf(PostgreSQLComponent.CNPG, PostgreSQLComponent.Stackgres)

        val allSelectableFlavours: NonEmptyList<Component<*>> =
            javaFlavours + pythonFlavours + rubyFlavours + rustFlavours + postgresFlavours

        fun conflictingFlavours(proposedFlavours: NonEmptyList<Component<*>>): List<Component<*>> =
            listOf(golangFlavours, javaFlavours, pythonFlavours, dotNetFlavours, rubyFlavours, rustFlavours).fold(emptyList()) {
                acc,
                flavourGroup ->
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
