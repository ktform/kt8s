import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {
  alias(libs.plugins.version.update)
  alias(libs.plugins.spotless) apply false
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.test) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.parcelize) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false
  alias(libs.plugins.google.services) apply false
  alias(libs.plugins.android.baselineprofile) apply false
  alias(libs.plugins.roborazzi) apply false
  alias(libs.plugins.compose.hot.reload) apply false
  alias(libs.plugins.compose.multiplatform) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.ktor) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
}

versionCatalogUpdate {
  versionSelector(VersionSelectors.PREFER_STABLE)
}
