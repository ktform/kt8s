package dev.ktform.kt8s.compiler

import arrow.core.getOrElse
import com.varabyte.truthish.assertThat
import dev.ktform.kt8s.compiler.CodegenGoldenFileTestCases.getOrUpdateExpected
import dev.ktform.kt8s.compiler.JSONSchema.toResources
import kotlin.test.Test
import kotlinx.io.files.Path

class KubernetesResourceCodegenTest {

    val targetDir = CodegenGoldenFileTestCases.TARGET_DIR

    @Test
    fun testResources() {
        val schema = JSONSchema.Version.V1_33_0
        val defs =
            JSONSchema.load(schema).getOrElse {
                throw IllegalStateException("Failed to load schema $schema")
            }

        defs.toResources("dev.ktform.kt8s.resources").forEach { res ->
            val actual = res.toString().trimIndent().trim()
            assertThat(actual)
                .isEqualTo(actual.getOrUpdateExpected(Path("$targetDir/${res.kind}.kt")))
        }
    }
}
