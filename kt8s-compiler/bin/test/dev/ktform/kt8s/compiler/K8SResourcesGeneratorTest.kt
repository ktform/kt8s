package dev.ktform.kt8s.compiler

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

class K8SResourcesGeneratorTest {
  @Test
  fun golden_generated_resources_content() {
    val version = JSONSchema.Version.V1_33_0

    val definitions = JSONSchema.load(version).fold(
      ifLeft = { error -> error("Failed to load schema ${version.value}: $error") },
      ifRight = { it }
    )

    val (_, _, content) = K8SResourceClientGenerator.buildResourcesRegistryContent(definitions)

    val goldenPath = Path.of("src/test/kotlin/dev/ktform/kt8s/compiler/golden/K8sResources_${version.value}.golden.kt")
    val expected = content.getOrUpdateExpectedKt(
      goldenFile = goldenPath,
      objectName = "K8sResourcesGolden_${sanitizeForIdentifier(version.value)}",
      packageName = "dev.ktform.kt8s.compiler.golden",
    )
    assertEquals(expected, content)
  }

  @Test
  fun testBuildResourcesRegistryContentIncludesCommonKinds() {
    val version = JSONSchema.Version.V1_33_0

    val definitions = JSONSchema.load(version).fold(
      ifLeft = { error -> error("Failed to load schema ${version.value}: $error") },
      ifRight = { it }
    )

    val (_, _, content) = K8SResourceClientGenerator.buildResourcesRegistryContent(definitions)

    // Expect well-known core types to be present
    assertTrue(content.contains("\"Pod\""), "Kinds should include Pod")
    assertTrue(content.contains("\"Service\""), "Kinds should include Service")

    // Expect at least one exact GVK tuple for Pod and Service in core v1
    assertTrue(content.contains("GVK(\"\", \"v1\", \"Pod\")"))
    assertTrue(content.contains("GVK(\"\", \"v1\", \"Service\")"))

    // Non-core example: Deployment in apps/v1
    assertTrue(content.contains("\"Deployment\""))
    assertTrue(content.contains("GVK(\"apps\", \"v1\", \"Deployment\")"))
  }
}

private fun sanitizeForIdentifier(s: String): String = s.replace("-", "_").replace(".", "_")

private fun readGoldenKt(goldenFile: Path): String? {
  if (!Files.exists(goldenFile)) return null
  val text = Files.readString(goldenFile)
  val regex = Regex("val\\s+expected\\s*:\\s*String\\s*=\\s*\"\"\"([\\s\\S]*?)\"\"\"")
  val match = regex.find(text)
  return match?.groupValues?.get(1)
}

private fun writeGoldenKt(
  expected: String,
  goldenFile: Path,
  objectName: String,
  packageName: String,
) {
  goldenFile.parent?.let { parent -> if (!Files.exists(parent)) Files.createDirectories(parent) }
  val content = buildString {
    appendLine("package $packageName")
    appendLine()
    appendLine("/** Auto-generated golden file. Edit by regenerating via tests. */")
    appendLine("object $objectName {")
    appendLine("  val expected: String = \"\"\"")
    appendLine(expected)
    appendLine("\"\"\"")
    appendLine("}")
  }
  Files.writeString(
    goldenFile,
    content,
    StandardOpenOption.CREATE,
    StandardOpenOption.TRUNCATE_EXISTING,
    StandardOpenOption.WRITE,
  )
}

private fun String.getOrUpdateExpectedKt(
  goldenFile: Path,
  objectName: String,
  packageName: String,
): String {
  val existing = readGoldenKt(goldenFile)
  if (existing == null) {
    try {
      writeGoldenKt(this, goldenFile, objectName, packageName)
      println("Added new golden test file: $goldenFile")
    } catch (e: Exception) {
      println("Warning: failed to write golden file: ${e.message}")
    }
    return this
  }
  if (existing != this) {
    println("Golden file mismatch: ${goldenFile}")
    println("Expected:\n$existing")
    println("Actual:\n$this")
    println("To update, delete the golden file and re-run tests")
  }
  return existing
}
