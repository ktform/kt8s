package dev.ktform.kt8s.container.packages.languages.ruby

import dev.ktform.kt8s.container.Environment
import dev.ktform.kt8s.container.PackageTestCase
import dev.ktform.kt8s.container.packages.Argo
import dev.ktform.kt8s.container.packages.languages.Ruby
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData

class RubyTest: FunSpec(
  {
    context("ruby") {
      withData(
        nameFn = { "ruby for ${it.name} ${it.env.distro.name} ${it.env.provider.name} should render correctly" },
        Environment.all.map { env ->
          PackageTestCase("ruby", env, rendered = Ruby().render())
        },
      ) {
        it.isExpected()
      }
    }
  },
)