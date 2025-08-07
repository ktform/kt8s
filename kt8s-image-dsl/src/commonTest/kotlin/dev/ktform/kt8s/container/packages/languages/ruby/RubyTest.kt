package dev.ktform.kt8s.container.packages.languages.ruby

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import dev.ktform.kt8s.container.packages.Argo
import dev.ktform.kt8s.container.packages.languages.Ruby
import kotlin.test.Test

class RubyTest {

  @Test
  fun testRuby() {
    Environment.all.forEach { env ->
      PackageTestCase("ruby", env, rendered = Ruby().render()).isExpected()
    }
  }
}