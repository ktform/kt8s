plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.maven.publish)
  alias(libs.plugins.kotest)
  id("maven-publish")
}

group = "dev.ktform.kt8s.charts"
version = libs.versions.settings.versionName.get()

android {
  namespace = "dev.ktform.kt8s.charts"
  compileSdk = libs.versions.settings.compileSdk.get().toInt()
  buildToolsVersion = libs.versions.settings.buildTools.get()

  defaultConfig {
    minSdk = libs.versions.settings.minSdk.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
}

kotlin {
  jvm()
  js {
    browser()
    nodejs()
  }
  iosX64()
  iosArm64()
  iosSimulatorArm64()
  androidTarget {
    publishLibraryVariants("release")
    publishLibraryVariantsGroupedByFlavor = true
  }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.bundles.common)
      implementation(libs.bundles.arrow)
    }
    commonTest.dependencies {
      implementation(libs.bundles.common.testing)
    }
    androidMain.dependencies {
      implementation(libs.bundles.android)
    }
    androidInstrumentedTest.dependencies {
      implementation(libs.bundles.android.testing)
    }
    jvmMain.dependencies {
      implementation(libs.bundles.jvm)
    }
    jvmTest.dependencies {
      implementation(libs.bundles.jvm.testing)
    }
    nativeMain.dependencies {
      implementation(libs.bundles.native)
    }
    nativeTest.dependencies {
//      implementation(libs.bundles.native.testing)
    }
    jsMain.dependencies {
      implementation(libs.bundles.js)
    }
    jsTest.dependencies {
      implementation(libs.bundles.js.testing)
    }
    iosMain.dependencies {
      implementation(libs.bundles.ios)
    }
    iosTest.dependencies {
      // implementation(libs.bundles.ios.testing)
    }
  }
}

dependencies {

}
