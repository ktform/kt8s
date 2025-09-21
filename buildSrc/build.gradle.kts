plugins {
    `kotlin-dsl`
    `version-catalog`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
  testImplementation("org.jetbrains.kotlin:kotlin-test:2.0.0")
  testImplementation("com.varabyte.truthish:truthish:1.0.3")
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}
