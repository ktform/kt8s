package dev.ktform.kt8s.container

import dev.ktform.kt8s.container.GoldenFileTestCases.Companion.getOrUpdateExpected
import io.kotest.matchers.shouldBe
import kotlinx.io.files.Path

data class PackageTestCase(
  val name: String,
  val env: Environment,
  val rendered: String,
) {
  private val goldenFile = Path("src/commonTest/resources/packages/${name.lowercase().replace(" ", "_")}.json")

  fun isExpected() {
    rendered shouldBe rendered.getOrUpdateExpected(env.provider.name.lowercase() + "_" + env.distro.name.lowercase(), goldenFile)
  }
}