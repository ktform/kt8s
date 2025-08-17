package dev.ktform.kt8s.container.packages.development


import arrow.core.getOrElse
import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class CosignTest {

  @Test
  fun testCosign() {
    runTest(timeout = 10.seconds) {
      // val latest = Cosign.`package`.latestVersion().getOrElse { err -> throw Exception("Unable to determine latest version: $err") }

      // Environment.all.forEach { env ->
      //   PackageTestCase(
      //     "cosign",
      //     env,
      //     rendered = Cosign(latest).render().getOrElse { err -> throw Exception("Unable to render: $err") },
      //   ).isExpected()
      // }
    }
  }

  @Test
  fun testCosignLatestVersions() {
    runTest(timeout = 10.seconds) {
      // val latestNVersions = Cosign.`package`.availableVersions(Environment.default)
      //   .getOrElse { err -> throw Exception("Unable to determine available versions: $err") }
      //   .take(Cosign.DEFAULT_VERSIONS.size)

      // assertThat(latestNVersions).isEqualTo(Cosign.DEFAULT_VERSIONS)
    }
  }
}