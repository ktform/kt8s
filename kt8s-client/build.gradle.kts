import dev.ktform.kt8s.build.kspKmp

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.maven.publish)

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

  targets.configureEach {
    compilations.configureEach {
      compileTaskProvider.get().compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
      }
    }
  }

  sourceSets {
    commonMain {
      kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
      dependencies {
        implementation(libs.bundles.common)
        implementation(libs.bundles.arrow)
      }
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
  add("kspCommonMainMetadata", project(":kt8s-compiler"))
}

// Ensure all Kotlin compilation tasks depend on KSP metadata generation
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
  if (name != "kspCommonMainKotlinMetadata") {
    dependsOn("kspCommonMainKotlinMetadata")
  }
}

// Additional explicit dependencies for specific task types
tasks.withType<org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile>().configureEach {
  dependsOn("kspCommonMainKotlinMetadata")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile>().configureEach {
  dependsOn("kspCommonMainKotlinMetadata")
}