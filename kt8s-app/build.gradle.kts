import dev.ktform.kt8s.build.kspKmp

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.maven.publish)


//  alias(libs.plugins.compose.hot.reload)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
  kotlin("multiplatform")
}

spotless {
  kotlin {
    licenseHeaderFile(rootProject.file("header.kt"))
    target("**/*.kt")
    targetExclude("**/build/**", "**/.gradle/**", "**/tmp/**", "**/resources/**")
    ktfmt("0.56").kotlinlangStyle()
    trimTrailingWhitespace()
    endWithNewline()
  }
}

group = "dev.ktform.kt8s"
version = libs.versions.settings.versionName.get()

android {
  namespace = "dev.ktform.kt8s"
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
    browser {
      testTask {
        useKarma {
          useFirefox()
        }
      }
    }
    nodejs {
      testTask {
        useMocha {
          timeout = "10000"
        }
      }
    }
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
//      implementation(libs.bundles.ios.testing)
    }
  }
}

dependencies {
  kspKmp(libs.arrow.optics.compiler)

}
