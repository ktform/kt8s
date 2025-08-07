package dev.ktform.kt8s.container

import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.GoldenFileTestCases.Companion.getOrUpdateExpected
import kotlinx.io.files.Path

data class PackageTestCase(
  val name: String,
  val env: Environment,
  val rendered: String,
) {
  private val goldenFile = Path("src/commonTest/resources/packages/${name.lowercase().replace(" ", "_")}.json")

  fun isExpected() {
    assertThat(rendered).isEqualTo(
      rendered.getOrUpdateExpected(
        env.provider.name.lowercase() + "_" + env.distro.name.lowercase(),
        goldenFile,
      ),
    )
  }
}