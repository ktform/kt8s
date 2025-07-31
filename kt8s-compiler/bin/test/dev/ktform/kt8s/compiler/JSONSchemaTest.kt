package dev.ktform.kt8s.compiler

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.ktform.kt8s.compiler.JSONSchema.getDefinitionsByGroupVersion
import dev.ktform.kt8s.compiler.JSONSchema.getDefinitionsByKind
import dev.ktform.kt8s.compiler.JSONSchema.resolveReference
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class JSONSchemaTest {

  data class SchemaTestCase(
    val name: String,
    val schema: String,
    val expectError: Boolean = false,
    val expectedErrorType: ((JSONSchema.ParseError) -> Boolean)? = null,
    val expectedDefinitionCount: Int = 1,
    val validate: (List<JSONSchema.Definition>) -> Either<String, Unit> = { Either.Right(Unit) }
  )

  private fun runSchemaTestCase(testCase: SchemaTestCase) {
    JSONSchema.parseSchemaString(testCase.schema).fold(
      ifLeft = { error ->
        if (testCase.expectError) {
          testCase.expectedErrorType?.invoke(error)?.let { assertTrue(it) }
            ?: error("Expected error type checker not provided")
        } else {
          throw AssertionError("${testCase.name}: Unexpected error: $error")
        }
      },
      ifRight = { definitions ->
        if (testCase.expectError) {
          error("${testCase.name}: Expected parse error, but parsing succeeded")
        } else {
          assertEquals(testCase.expectedDefinitionCount, definitions.size)
          testCase.validate(definitions).fold(
            ifLeft = { errorMsg -> throw AssertionError(errorMsg) },
            ifRight = { /* success */ }
          )
        }
      }
    )
  }

  @Test
  fun testBasicObjectWithStringAndIntegerProperties() {
    val testCase = SchemaTestCase(
      name = "basic object with string and integer properties",
      schema = """
        {
          "definitions": {
            "TestObject": {
              "type": "object",
              "properties": {
                "name": {"type": "string"},
                "count": {"type": "integer"}
              }
            }
          }
        }
      """.trimIndent()
    ) { definitions ->
      either {
        val testObject = definitions.find { it.name == "TestObject" }
          .toOption().toEither { "TestObject definition not found" }.bind()

        ensure(testObject.properties.size == 2) { "Expected 2 properties, got ${testObject.properties.size}" }
        ensure(testObject.properties[0].type == JSONSchema.Type.STRING) { "First property should be STRING" }
        ensure(testObject.properties[1].type == JSONSchema.Type.INTEGER) { "Second property should be INTEGER" }
      }
    }
    runSchemaTestCase(testCase)
  }

  @Test
  fun testStringFormats() {
    val testCase = SchemaTestCase(
      name = "string formats (date-time, byte)",
      schema = """
        {
          "definitions": {
            "FormattedObject": {
              "type": "object",
              "properties": {
                "timestamp": {"type": "string", "format": "date-time"},
                "data": {"type": "string", "format": "byte"}
              }
            }
          }
        }
      """.trimIndent()
    ) { definitions ->
      either {
        val obj = definitions.find { it.name == "FormattedObject" }
          .toOption().toEither { "FormattedObject not found" }.bind()

        val timestampProp = obj.properties.find { it.name == "timestamp" }
          .toOption().toEither { "timestamp property not found" }.bind()
        val dataProp = obj.properties.find { it.name == "data" }
          .toOption().toEither { "data property not found" }.bind()

        ensure(timestampProp.format == Some(JSONSchema.Format.DATE_TIME)) { "timestamp format mismatch" }
        ensure(dataProp.format == Some(JSONSchema.Format.BYTE)) { "data format mismatch" }
      }
    }
    runSchemaTestCase(testCase)
  }

  @Test
  fun testNumberFormats() {
    val testCase = SchemaTestCase(
      name = "number formats (int32, int64, double, float->double)",
      schema = """
        {
          "definitions": {
            "NumberObject": {
              "type": "object",
              "properties": {
                "int32Val": {"type": "integer", "format": "int32"},
                "int64Val": {"type": "integer", "format": "int64"},
                "doubleVal": {"type": "number", "format": "double"},
                "floatVal": {"type": "number", "format": "float"}
              }
            }
          }
        }
      """.trimIndent()
    ) { definitions ->
      either {
        val obj = definitions.find { it.name == "NumberObject" }
          .toOption().toEither { "NumberObject not found" }.bind()

        val int32Prop = obj.properties.find { it.name == "int32Val" }!!
        val int64Prop = obj.properties.find { it.name == "int64Val" }!!
        val doubleProp = obj.properties.find { it.name == "doubleVal" }!!
        val floatProp = obj.properties.find { it.name == "floatVal" }!!

        ensure(int32Prop.format == Some(JSONSchema.Format.INT32)) { "int32 format mismatch" }
        ensure(int64Prop.format == Some(JSONSchema.Format.INT64)) { "int64 format mismatch" }
        ensure(doubleProp.format == Some(JSONSchema.Format.DOUBLE)) { "double format mismatch" }
        ensure(floatProp.format == Some(JSONSchema.Format.DOUBLE)) { "float should convert to double" }
      }
    }
    runSchemaTestCase(testCase)
  }

  @Test
  fun testObjectReferencesAndArrays() {
    val testCase = SchemaTestCase(
      name = "object references and arrays",
      schema = $$"""
        {
          "definitions": {
            "Container": {
              "type": "object",
              "properties": {
                "items": {
                  "type": "array",
                  "items": {"type": "string"}
                },
                "address": {"$ref": "#/definitions/Address"}
              }
            },
            "Address": {
              "type": "object",
              "properties": {
                "street": {"type": "string"}
              }
            }
          }
        }
      """.trimIndent(),
      expectedDefinitionCount = 2
    ) { definitions ->
      either {
        val container = definitions.find { it.name == "Container" }
          .toOption().toEither { "Container not found" }.bind()

        val itemsProp = container.properties.find { it.name == "items" }!!
        val addressProp = container.properties.find { it.name == "address" }!!

        ensure(itemsProp.type == JSONSchema.Type.ARRAY) { "items should be ARRAY type" }
        ensure(itemsProp.items.isSome()) { "array items should be defined" }
        ensure(itemsProp.items.getOrNull()?.type == JSONSchema.Type.STRING) { "array items should be STRING" }

        ensure(addressProp.type == JSONSchema.Type.OBJECT) { "address should be OBJECT type" }
        ensure(addressProp.format == Some(JSONSchema.Format.OBJECT_REF)) { "address should have OBJECT_REF format" }
        ensure(addressProp.ref == Some("Address")) { "address ref should point to Address" }
      }
    }
    runSchemaTestCase(testCase)
  }

  @Test
  fun testKubernetesGroupVersionKindMetadata() {
    val testCase = SchemaTestCase(
      name = "kubernetes group-version-kind metadata",
      schema = """
        {
          "definitions": {
            "Pod": {
              "type": "object",
              "x-kubernetes-group-version-kind": [
                {"group": "", "version": "v1", "kind": "Pod"}
              ],
              "properties": {"name": {"type": "string"}}
            },
            "Service": {
              "type": "object",
              "x-kubernetes-group-version-kind": [
                {"group": "", "version": "v1", "kind": "Service"}
              ],
              "properties": {"name": {"type": "string"}}
            }
          }
        }
      """.trimIndent(),
      expectedDefinitionCount = 2
    ) { definitions ->
      either {
        val podDefs = definitions.getDefinitionsByKind("Pod")
        val v1Defs = definitions.getDefinitionsByGroupVersion("", "v1")

        ensure(podDefs.size == 1) { "Should find exactly 1 Pod definition" }
        ensure(podDefs[0].kubernetesGroupVersionKind[0].third == "Pod") { "Pod kind mismatch" }
        ensure(v1Defs.size == 2) { "Should find 2 v1 definitions" }
      }
    }
    runSchemaTestCase(testCase)
  }

  @Test
  fun testInvalidJson() {
    val testCase = SchemaTestCase(
      name = "invalid JSON",
      schema = "{ invalid }",
      expectError = true,
      expectedErrorType = { it is JSONSchema.ParseError.InvalidSchema }
    )
    runSchemaTestCase(testCase)
  }

  @Test
  fun testMissingDefinitions() {
    val testCase = SchemaTestCase(
      name = "missing definitions",
      schema = """{"other": "value"}""",
      expectError = true,
      expectedErrorType = { it is JSONSchema.ParseError.MissingDefinitions }
    )
    runSchemaTestCase(testCase)
  }

  @Test
  fun testUnsupportedType() {
    val testCase = SchemaTestCase(
      name = "unsupported type",
      schema = """
        {"definitions": {"Bad": {"type": "object", "properties": {"bad": {"type": "unknown"}}}}}
      """.trimIndent(),
      expectError = true,
      expectedErrorType = { it is JSONSchema.ParseError.UnsupportedType }
    )
    runSchemaTestCase(testCase)
  }

  @Test
  fun testArrayWithoutItems() {
    val testCase = SchemaTestCase(
      name = "array without items",
      schema = """
        {"definitions": {"Bad": {"type": "object", "properties": {"arr": {"type": "array"}}}}}
      """.trimIndent(),
      expectError = true,
      expectedErrorType = { it is JSONSchema.ParseError.InvalidProperty }
    )
    runSchemaTestCase(testCase)
  }

  @Test
  fun testAllSchemaVersionsShouldBeLoadable() {
    val (failed, successful) = JSONSchema.Version.entries
      .map { version -> version to JSONSchema.load(version) }
      .partition { (_, result) -> result.isLeft() }

    // Log successful loads
    successful.forEach { (version, result) ->
      result.fold(
        ifLeft = { /* should not happen */ },
        ifRight = { definitions ->
          println("✓ ${version.value}: ${definitions.size} definitions")
        }
      )
    }

    // Verify failed loads have expected error types
    failed.forEach { (version, result) ->
      result.fold(
        ifLeft = { error ->
          assertTrue(error is JSONSchema.ParseError.FileError || error is JSONSchema.ParseError.InvalidSchema)
        },
        ifRight = { error("Failed to load ${version.value}") }
      )
    }

    assertTrue(failed.isEmpty())
    assertEquals(JSONSchema.Version.entries.size, successful.size + failed.size)
  }

  @Test
  fun testReferenceResolution() {
    val version = JSONSchema.Version.V1_32_0

    JSONSchema.load(version).fold(
      ifLeft = { error -> error("Failed to load schema version ${version.value}: $error") },
      ifRight = { definitions ->
        val objectRefs = mutableListOf<Pair<String, String>>()

        // Collect all object references
        definitions.forEach { definition ->
          definition.properties.forEach { property ->
            if (property.format == Some(JSONSchema.Format.OBJECT_REF)) {
              property.ref.fold(
                { throw AssertionError("Property ${property.name} in ${definition.name} has OBJECT_REF format but no ref value") },
                { refValue -> objectRefs.add(definition.name to refValue) }
              )
            }
          }
        }

        assertTrue(objectRefs.isNotEmpty())
        println("Found ${objectRefs.size} object references")

        // Verify all references can be resolved
        objectRefs.forEach { (fromDef, toDef) ->
          val resolved = definitions.resolveReference(toDef)
          assertTrue(resolved.isSome())
          assertEquals(toDef, resolved.map { it.name }.getOrNull())
        }
      }
    )
  }
}
