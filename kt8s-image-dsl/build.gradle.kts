import dev.ktform.kt8s.build.kspKmp

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.maven.publish)
  alias(libs.plugins.kotest)
}

group = "dev.ktform.kt8s.container"
version = libs.versions.settings.versionName.get()

android {
  namespace = "dev.ktform.kt8s.container"
  compileSdk = libs.versions.settings.compileSdk.get().toInt()
  buildToolsVersion = libs.versions.settings.buildTools.get()

  defaultConfig {
    minSdk = libs.versions.settings.minSdk.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }

  testOptions {
    unitTests.all {
      it.useJUnitPlatform()
    }
  }
}

kotlin {
  jvm {
    tasks.withType<Test>().configureEach {
      useJUnitPlatform()
    }
  }
  js {
    nodejs()
    browser()
  }
  iosX64()
  iosArm64()
  iosSimulatorArm64()
  androidTarget {
    publishLibraryVariants("release")
    publishLibraryVariantsGroupedByFlavor = true
  }
  sourceSets {
    androidMain.dependencies {
      implementation(libs.bundles.android)
    }
    androidUnitTest.dependencies {
      implementation(libs.bundles.android.testing)
      implementation(libs.testing.junit)
      runtimeOnly(libs.testing.junit.engine)
    }
    jsMain.dependencies {
      implementation(libs.bundles.js)
    }
    jsTest.dependencies {
      implementation(libs.bundles.js.testing)
    }
    commonMain.dependencies {
      implementation(libs.bundles.common)
      implementation(libs.bundles.arrow)
    }
    commonTest.dependencies {
      implementation(libs.bundles.common.testing)
    }
    jvmMain.dependencies {
      implementation(libs.bundles.jvm)
      implementation(libs.testing.junit)
      runtimeOnly(libs.testing.junit.engine)
    }
    jvmTest.dependencies {
      implementation(libs.bundles.jvm.testing)
    }
  }
}

dependencies {
  kspKmp(libs.arrow.optics.compiler)
}
