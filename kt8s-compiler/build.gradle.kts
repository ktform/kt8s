plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.ktor)
  kotlin("jvm")
}

spotless {
  kotlin {
    ktfmt("0.56").kotlinlangStyle()
  }
}
