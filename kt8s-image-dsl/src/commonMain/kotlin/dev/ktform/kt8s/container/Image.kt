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

/**
 * Enhanced Image DSL with distro-specific configuration
 */
@ImageDsl
class Image {
  data class BuildStep(
    val before: List<String> = emptyList(),
    val commands: List<String> = emptyList(),
    val after: List<String> = emptyList(),
  )

  data class BuildConfig(
    val all: BuildStep = BuildStep(),
    val distroSpecific: Map<Distro, BuildStep> = emptyMap(),
  )

  data class Endpoints(
    val healthcheck: Option<String> = none(),
    val metrics: Option<String> = none(),
    val service: Option<String> = none(),
  )

  data class Probes(
    val liveness: Option<String> = none(),
    val readiness: Option<String> = none(),
    val startup: Option<String> = none(),
  )

  data class SignalConfig(
    val stopGracefully: Option<Signal> = none(),
    val stopImmediately: Option<Signal> = none(),
    val reloadConfig: Option<Signal> = none(),
  )

  data class Dependencies(
    val build: List<String> = emptyList(),
    val run: List<String> = emptyList(),
    val buildAndRun: List<String> = emptyList(),
  ) {
    fun getAllDependencies(): List<String> = (build + run + buildAndRun).distinct()
    fun getBuildDependencies(): List<String> = (build + buildAndRun).distinct()
    fun getRunDependencies(): List<String> = (run + buildAndRun).distinct()
  }

  data class DistroConfig(
    val distro: Distro,
    val dependencies: Dependencies = Dependencies(),
    val buildConfig: BuildConfig = BuildConfig(),
    val endpoints: Endpoints = Endpoints(),
    val probes: Probes = Probes(),
    val signalConfig: SignalConfig = SignalConfig(),
  )

  @ImageDsl
  class DistroBuilder(private val distro: Distro) {
    private var dependencies = Dependencies()
    private var buildConfig = BuildConfig()
    private var endpoints = Endpoints()
    private var probes = Probes()
    private var signalConfig = SignalConfig()

    fun dependencies(block: DependenciesBuilder.() -> Unit) {
      val builder = DependenciesBuilder()
      builder.block()
      dependencies = builder.build()
    }

    fun build(block: BuildConfigBuilder.() -> Unit) {
      val builder = BuildConfigBuilder()
      builder.block()
      buildConfig = builder.build()
    }

    fun endpoints(block: EndpointsBuilder.() -> Unit) {
      val builder = EndpointsBuilder()
      builder.block()
      endpoints = builder.build()
    }

    fun probes(block: ProbesBuilder.() -> Unit) {
      val builder = ProbesBuilder()
      builder.block()
      probes = builder.build()
    }

    fun stopGracefullySignal(signal: Signal) {
      signalConfig = signalConfig.copy(stopGracefully = signal.some())
    }

    fun stopImmediatelySignal(signal: Signal) {
      signalConfig = signalConfig.copy(stopImmediately = signal.some())
    }

    fun reloadConfigSignal(signal: Signal) {
      signalConfig = signalConfig.copy(reloadConfig = signal.some())
    }

    internal fun build(): DistroConfig = DistroConfig(
      distro = distro,
      dependencies = dependencies,
      buildConfig = buildConfig,
      endpoints = endpoints,
      probes = probes,
      signalConfig = signalConfig,
    )
  }

  @ImageDsl
  class DependenciesBuilder {
    private val buildDeps = mutableListOf<String>()
    private val runDeps = mutableListOf<String>()
    private val buildAndRunDeps = mutableListOf<String>()

    fun buildAndRun(vararg packages: String) {
      buildAndRunDeps.addAll(packages)
    }

    fun build(vararg packages: String) {
      buildDeps.addAll(packages)
    }

    fun run(vararg packages: String) {
      runDeps.addAll(packages)
    }

    internal fun build(): Dependencies = Dependencies(
      build = buildDeps.toList(),
      run = runDeps.toList(),
      buildAndRun = buildAndRunDeps.toList(),
    )
  }

  @ImageDsl
  class BuildConfigBuilder {
    private var allStep = BuildStep()
    private val distroSteps = mutableMapOf<Distro, BuildStep>()

    fun all(vararg commands: String) {
      allStep = BuildStep(commands = commands.toList())
    }

    fun debian(vararg commands: String) {
      distroSteps[Distro.Debian] = BuildStep(commands = commands.toList())
    }

    fun alpine(vararg commands: String) {
      distroSteps[Distro.Alpine] = BuildStep(commands = commands.toList())
    }

    internal fun build(): BuildConfig = BuildConfig(
      all = allStep,
      distroSpecific = distroSteps.toMap(),
    )
  }

  @ImageDsl
  class EndpointsBuilder {
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

  @ImageDsl
  class ProbesBuilder {
    private var livenessProbe: Option<String> = none()
    private var readinessProbe: Option<String> = none()
    private var startupProbe: Option<String> = none()

    fun liveness(command: String) {
      livenessProbe = command.some()
    }

    fun readiness(command: String) {
      readinessProbe = command.some()
    }

    fun startup(command: String) {
      startupProbe = command.some()
    }

    internal fun build(): Probes = Probes(
      liveness = livenessProbe,
      readiness = readinessProbe,
      startup = startupProbe,
    )
  }

  @ImageDsl
  class SignalConfigBuilder {
    private var stopGracefully: Option<Signal> = none()
    private var stopImmediately: Option<Signal> = none()
    private var reloadConfig: Option<Signal> = none()

    fun stopGracefully(signal: Signal) {
      stopGracefully = signal.some()
    }

    fun stopImmediately(signal: Signal) {
      stopImmediately = signal.some()
    }

    fun reloadConfig(signal: Signal) {
      reloadConfig = signal.some()
    }

    internal fun build(): SignalConfig = SignalConfig(
      stopGracefully = stopGracefully,
      stopImmediately = stopImmediately,
      reloadConfig = reloadConfig,
    )
  }

  private val distroConfigs = mutableMapOf<Distro, DistroConfig>()

  fun distro(name: String, block: DistroBuilder.() -> Unit) {
    val distro = when (name.lowercase()) {
      "alpine" -> Distro.Alpine
      "debian" -> Distro.Debian
      else -> throw IllegalArgumentException("Unknown distro: $name. Supported: alpine, ubuntu, debian, centos, fedora")
    }

    val builder = DistroBuilder(distro)
    builder.block()
    distroConfigs[distro] = builder.build()
  }

  fun toPackage(name: String): Package {
    // Convert the enhanced DSL configuration to Package format
    val allDependencies = mutableMapOf<Distro, NonEmptyList<Package>>()
    val buildCommands = mutableMapOf<Distro, NonEmptyList<String>>()

    distroConfigs.forEach { (distro, config) ->
      // Convert dependencies to packages
      val deps = config.dependencies.getAllDependencies().mapNotNull { depName ->
        Package.all[depName]
      }
      if (deps.isNotEmpty()) {
        allDependencies[distro] = deps.toNonEmptyListOrThrow()
      }

      // Build commands - combine all steps in order
      val commands = mutableListOf<String>()

      // Add global commands
      commands.addAll(config.buildConfig.all.commands)

      // Add distro-specific commands
      config.buildConfig.distroSpecific[distro]?.commands?.let { commands.addAll(it) }

      if (commands.isNotEmpty()) {
        buildCommands[distro] = commands.toNonEmptyListOrThrow()
      }
    }

    return Package(
      packageName = name,
      runtime = true,
      dependencies = allDependencies,
      build = buildCommands,
    )
  }
}
