import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {
  alias(libs.plugins.version.update)
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.spotless) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.maven.publish) apply false
}

versionCatalogUpdate {
  versionSelector(VersionSelectors.PREFER_STABLE)
}
