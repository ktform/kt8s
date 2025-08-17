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

import arrow.core.NonEmptyList
import arrow.core.Option
import arrow.core.nonEmptyListOf
import arrow.core.toOption
import dev.ktform.kt8s.container.Versions.*
import io.ktor.util.*

sealed interface Component<T : Versions<T>> {
  val name: String
  val applicableFlavours: List<Component<*>>

  val applicableProviders: List<Provider>

  companion object {
    val defaultFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(
      JavaComponent.OpenJDK,
      PythonComponent.CPython,
      RubyComponent.Ruby,
      RustComponent.Stable,
    )

    val javaFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(
      JavaComponent.OpenJDK,
      JavaComponent.GraalVM,
      JavaComponent.OpenJ9,
    )

    val pythonFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(
      PythonComponent.CPython,
      PythonComponent.PyPy,
    )

    val rubyFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(
      RubyComponent.Ruby,
      RubyComponent.MRuby,
    )

    val rustFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(
      RustComponent.Stable,
      RustComponent.Nightly,
    )

    val postgresFlavours: NonEmptyList<Component<*>> = nonEmptyListOf(
      PostgreSQLComponent.CNPG,
      PostgreSQLComponent.Stackgres,
    )

    val allFlavours: NonEmptyList<Component<*>> = javaFlavours + pythonFlavours + rubyFlavours + rustFlavours + postgresFlavours

    fun conflictingFlavours(proposedFlavours: NonEmptyList<Component<*>>): List<Component<*>> =
      listOf(javaFlavours, pythonFlavours, rubyFlavours, rustFlavours).fold(emptyList()) { acc, flavourGroup ->
        val conflictsInGroup = proposedFlavours.filter { it in flavourGroup }
        if (conflictsInGroup.size > 1) acc + conflictsInGroup else acc
      }

    fun flavourByName(name: String): Option<Component<*>> =
      allFlavours.find { it.name.toLowerCasePreservingASCIIRules() == name.toLowerCasePreservingASCIIRules() }.toOption()
  }
}