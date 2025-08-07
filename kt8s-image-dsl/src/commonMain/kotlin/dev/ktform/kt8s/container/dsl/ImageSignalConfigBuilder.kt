package dev.ktform.kt8s.container.dsl

import arrow.core.Option
import arrow.core.none
import arrow.core.some

@ImageDsl
class ImageSignalConfigBuilder {
  data class Signals(
    val stopGracefully: Option<dev.ktform.kt8s.container.Signal> = none(),
    val stopImmediately: Option<dev.ktform.kt8s.container.Signal> = none(),
    val reloadConfig: Option<dev.ktform.kt8s.container.Signal> = none(),
  )

  private var stopGracefully: Option<dev.ktform.kt8s.container.Signal> = none()
  private var stopImmediately: Option<dev.ktform.kt8s.container.Signal> = none()
  private var reloadConfig: Option<dev.ktform.kt8s.container.Signal> = none()

  fun stopGracefully(signal: dev.ktform.kt8s.container.Signal) {
    stopGracefully = signal.some()
  }

  fun stopImmediately(signal: dev.ktform.kt8s.container.Signal) {
    stopImmediately = signal.some()
  }

  fun reloadConfig(signal: dev.ktform.kt8s.container.Signal) {
    reloadConfig = signal.some()
  }

  internal fun build(): Signals = Signals(
    stopGracefully = stopGracefully,
    stopImmediately = stopImmediately,
    reloadConfig = reloadConfig,
  )
}
