package dev.ktform.kt8s.compiler

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import dev.ktform.kt8s.compiler.JSONSchema.getDefinitionsByGroupVersion
import dev.ktform.kt8s.compiler.JSONSchema.getDefinitionsByKind
import dev.ktform.kt8s.compiler.JSONSchema.resolveReference
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull

class JSONSchemaTest : FunSpec({

  test("schema parsing functionally") {
    data class SchemaTest(
      val name: String,
      val schema: String,
      val expectedCount: Int,
      val validate: (List<JSONSchema.Definition>) -> Either<String, Unit>
    )

    val schemaTests = listOf(
      SchemaTest("basic schema", """
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
      """.trimIndent(), 1) { definitions ->
        either {
          val testObject = definitions.find { it.name == "TestObject" }
            .toOption()
            .toEither { "TestObject definition not found" }
            .bind()

          ensure(testObject.properties.isNotEmpty()) {
            "Property index 0 out of bounds (${testObject.properties.size} properties)"
          }

          val property = testObject.properties[0]
          ensure(property.type == JSONSchema.Type.STRING) {
            "Expected type ${JSONSchema.Type.STRING}, got ${property.type}"
          }

          ensure(property.format == None) {
            "Expected format ${None}, got ${property.format}"
          }

          // Second property validation
          ensure(testObject.properties.size > 1) {
            "Property index 1 out of bounds (${testObject.properties.size} properties)"
          }

          val property2 = testObject.properties[1]
          ensure(property2.type == JSONSchema.Type.INTEGER) {
            "Expected type ${JSONSchema.Type.INTEGER}, got ${property2.type}"
          }

          ensure(property2.format == None) {
            "Expected format ${None}, got ${property2.format}"
          }
        }
      },

      SchemaTest("format parsing", """
        {
          "definitions": {
            "TestObject": {
              "type": "object",
              "properties": {
                "timestamp": {"type": "string", "format": "date-time"},
                "data": {"type": "string", "format": "byte"}
              }
            }
          }
        }
      """.trimIndent(), 1) { definitions ->
        either {
          val testObject = definitions.find { it.name == "TestObject" }
            .toOption()
            .toEither { "TestObject definition not found" }
            .bind()

          ensure(testObject.properties.isNotEmpty()) {
            "Property index 0 out of bounds (${testObject.properties.size} properties)"
          }

          val property = testObject.properties[0]
          ensure(property.type == JSONSchema.Type.STRING) {
            "Expected type ${JSONSchema.Type.STRING}, got ${property.type}"
          }

          ensure(property.format == Some(JSONSchema.Format.DATE_TIME)) {
            "Expected format ${Some(JSONSchema.Format.DATE_TIME)}, got ${property.format}"
          }

          // Second property validation
          ensure(testObject.properties.size > 1) {
            "Property index 1 out of bounds (${testObject.properties.size} properties)"
          }

          val property2 = testObject.properties[1]
          ensure(property2.type == JSONSchema.Type.STRING) {
            "Expected type ${JSONSchema.Type.STRING}, got ${property2.type}"
          }

          ensure(property2.format == Some(JSONSchema.Format.BYTE)) {
            "Expected format ${Some(JSONSchema.Format.BYTE)}, got ${property2.format}"
          }
        }
      }
    )

    schemaTests.forEach { test ->
      JSONSchema.parseSchemaString(test.schema).fold(
        ifLeft = { error -> throw AssertionError("${test.name}: $error") },
        ifRight = { definitions ->
          definitions shouldHaveSize test.expectedCount
          test.validate(definitions) shouldBeRight Unit
        }
      )
    }
  }

  test("property parsing functionally") {
    data class PropertyTest(
      val name: String,
      val json: String,
      val expectedType: JSONSchema.Type,
      val expectedFormat: Option<JSONSchema.Format> = None
    )

    val propertyTests = listOf(
      PropertyTest("string property", """{"type": "string"}""", JSONSchema.Type.STRING),
      PropertyTest("integer property", """{"type": "integer"}""", JSONSchema.Type.INTEGER),
      PropertyTest("boolean property", """{"type": "boolean"}""", JSONSchema.Type.BOOLEAN),
      PropertyTest("array property", """{"type": "array", "items": {"type": "string"}}""", JSONSchema.Type.ARRAY),
      PropertyTest("date-time format", """{"type": "string", "format": "date-time"}""", JSONSchema.Type.STRING, Some(JSONSchema.Format.DATE_TIME)),
      PropertyTest("byte format", """{"type": "string", "format": "byte"}""", JSONSchema.Type.STRING, Some(JSONSchema.Format.BYTE))
    )

    propertyTests.forEach { test ->
      val schema = """
        {
          "definitions": {
            "TestObject": {
              "type": "object",
              "properties": {
                "testProp": ${test.json}
              }
            }
          }
        }
      """.trimIndent()

      JSONSchema.parseSchemaString(schema).fold(
        ifLeft = { error -> ("${test.name}: Parse failed: $error") },
        ifRight = { definitions ->
          val testObject = definitions.find { it.name == "TestObject" }!!
          val property = testObject.properties[0]

          property.type shouldBe test.expectedType
          property.format shouldBe test.expectedFormat
        }
      )
    }
  }

  test("object references") {
    val schema = $$"""
      {
        "definitions": {
          "Person": {
            "type": "object",
            "x-kubernetes-group-version-kind": [
              {"group": "", "version": "v1", "kind": "Person"}
            ],
            "properties": {
              "name": {"type": "string"},
              "address": {"$ref": "#/definitions/Address"}
            }
          },
          "Address": {
            "type": "object",
            "x-kubernetes-group-version-kind": [
              {"group": "", "version": "v1", "kind": "Address"}
            ],
            "properties": {
              "street": {"type": "string"}
            }
          }
        }
      }
    """.trimIndent()

    JSONSchema.parseSchemaString(schema).fold(
      ifLeft = { error -> error("Parse failed: $error") },
      ifRight = { definitions ->
        val person = definitions.find { it.name == "Person" }!!
        val addressProperty = person.properties.find { it.name == "address" }!!

        addressProperty.type shouldBe JSONSchema.Type.OBJECT
        addressProperty.format shouldBe Some(JSONSchema.Format.OBJECT_REF)
        addressProperty.ref shouldBe Some("Address")
      }
    )
  }

  test("array properties") {
    val schema = """
      {
        "definitions": {
          "Container": {
            "type": "object",
            "properties": {
              "items": {
                "type": "array",
                "items": {"type": "string"}
              }
            }
          }
        }
      }
    """.trimIndent()

    JSONSchema.parseSchemaString(schema).fold(
      ifLeft = { error -> error("Parse failed: $error") },
      ifRight = { definitions ->
        val container = definitions.find { it.name == "Container" }!!
        val itemsProperty = container.properties[0]

        itemsProperty.type shouldBe JSONSchema.Type.ARRAY
        itemsProperty.items.isSome().shouldBeTrue()
        itemsProperty.items.getOrNull()?.type shouldBe JSONSchema.Type.STRING
      }
    )
  }

  test("error handling functionally") {
    data class ErrorTest(
      val name: String,
      val schema: String,
      val expectedError: (JSONSchema.ParseError) -> Boolean
    )

    val errorTests = listOf(
      ErrorTest("invalid json", "{ invalid }") { it is JSONSchema.ParseError.InvalidSchema },
      ErrorTest("missing definitions", """{"other": "value"}""") { it is JSONSchema.ParseError.MissingDefinitions },
      ErrorTest("unsupported type", """
        {"definitions": {"Bad": {"type": "object", "properties": {"bad": {"type": "unknown"}}}}}
      """.trimIndent()
      ) { it is JSONSchema.ParseError.UnsupportedType },
      ErrorTest("array without items", """
        {"definitions": {"Bad": {"type": "object", "properties": {"arr": {"type": "array"}}}}}
      """.trimIndent()
      ) { it is JSONSchema.ParseError.InvalidProperty }
    )

    errorTests.forEach { test ->
      JSONSchema.parseSchemaString(test.schema).fold(
        ifLeft = { error ->
          test.expectedError(error).shouldBeTrue()
        },
        ifRight = { error("${test.name}: Expected parse error, but parsing succeeded") }
      )
    }
  }

  test("query operations") {
    val schema = """
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
    """.trimIndent()

    JSONSchema.parseSchemaString(schema).fold(
      ifLeft = { error -> error("Parse failed: $error") },
      ifRight = { definitions ->
        val podDefinitions = definitions.getDefinitionsByKind("Pod")
        podDefinitions shouldHaveSize 1
        podDefinitions[0].kubernetesGroupVersionKind[0].third shouldBe "Pod"

        val v1Definitions = definitions.getDefinitionsByGroupVersion("", "v1")
        v1Definitions shouldHaveSize 2
      }
    )
  }

  test("loading") {
    val (failed, successful) = JSONSchema.Version.entries
      .map { version -> version to JSONSchema.load(version) }
      .partition { (_, result) -> result.isLeft() }

    // Log results for debugging
    successful.forEach { (version, result) ->
      result.fold(
        ifLeft = { /* should not happen */ },
        ifRight = { definitions ->
          println("✓ ${version.value}: ${definitions.size} definitions")
        }
      )
    }

    failed.forEach { (version, result) ->
      result.fold(
        ifLeft = { error ->
          (error is JSONSchema.ParseError.FileError || error is JSONSchema.ParseError.InvalidSchema).shouldBeTrue()
        },
        ifRight = { error("Failed to load ${version.value}") }
      )
    }

    failed.isEmpty().shouldBeTrue()
    (successful.size + failed.size) shouldBe JSONSchema.Version.entries.size
  }

  test("single version loading") {
    JSONSchema.Version.entries.forEach { version ->
      JSONSchema.load(version).fold(
        ifLeft = { error ->
          (error is JSONSchema.ParseError.FileError || error is JSONSchema.ParseError.InvalidSchema).shouldBeTrue()
        },
        ifRight = { definitions ->
          definitions.isNotEmpty().shouldBeTrue()
          definitions.forEach { definition ->
            definition.name.shouldNotBeNull()
          }
        }
      )
    }
  }

  test("nested definitions are resolved correctly") {
    // Test with a specific version to avoid running this on all versions
    val version = JSONSchema.Version.V1_32_0

    JSONSchema.load(version).fold(
      ifLeft = { error -> error("Failed to load schema version ${version.value}: $error") },
      ifRight = { definitions ->
        // Track all object references to ensure they can be resolved
        val objectRefs = mutableListOf<Pair<String, String>>() // (from, to)

        // Scan all definitions for object references
        definitions.forEach { definition ->
          definition.properties.forEach { property ->
            if (property.format == Some(JSONSchema.Format.OBJECT_REF)) {
              property.ref.fold(
                { throw AssertionError("Property ${property.name} in ${definition.name} has OBJECT_REF format but no ref value") },
                { refValue ->
                  objectRefs.add(definition.name to refValue)
                }
              )
            }
          }
        }

        // Ensure we found at least some object references
        objectRefs.isNotEmpty().shouldBeTrue()
        println("Found ${objectRefs.size} object references")

        // Verify all references can be resolved
        objectRefs.forEach { (fromDef, toDef) ->
          val resolved = definitions.resolveReference(toDef)
          (fromDef to resolved.isSome()) shouldBe (fromDef to true)
          resolved.map { it.name }.getOrNull() shouldBe toDef
        }
      }
    )
  }

  test("format parsing with table-driven tests") {
    data class FormatTest(
      val name: String,
      val type: String,
      val format: String?,
      val expectedType: JSONSchema.Type,
      val expectedFormat: Option<JSONSchema.Format>
    )

    val formatTests = listOf(
      FormatTest(
        name = "int32 format",
        type = "integer",
        format = "int32",
        expectedType = JSONSchema.Type.INTEGER,
        expectedFormat = Some(JSONSchema.Format.INT32)
      ),
      FormatTest(
        name = "int64 format",
        type = "integer",
        format = "int64",
        expectedType = JSONSchema.Type.INTEGER,
        expectedFormat = Some(JSONSchema.Format.INT64)
      ),
      FormatTest(
        name = "date-time format",
        type = "string",
        format = "date-time",
        expectedType = JSONSchema.Type.STRING,
        expectedFormat = Some(JSONSchema.Format.DATE_TIME)
      ),
      FormatTest(
        name = "byte format",
        type = "string",
        format = "byte",
        expectedType = JSONSchema.Type.STRING,
        expectedFormat = Some(JSONSchema.Format.BYTE)
      ),
      FormatTest(
        name = "double format",
        type = "number",
        format = "double",
        expectedType = JSONSchema.Type.NUMBER,
        expectedFormat = Some(JSONSchema.Format.DOUBLE)
      ),
      FormatTest(
        name = "float format converted to double",
        type = "number",
        format = "float",
        expectedType = JSONSchema.Type.NUMBER,
        expectedFormat = Some(JSONSchema.Format.DOUBLE)
      )
    )

    formatTests.forEach { test ->
      val formatJson = if (test.format != null) """, "format": "${test.format}"""" else ""
      val schema = """
        {
          "definitions": {
            "TestType": {
              "type": "object",
              "properties": {
                "value": {
                  "type": "${test.type}"$formatJson
                }
              }
            }
          }
        }
      """.trimIndent()

      JSONSchema.parseSchemaString(schema).fold(
        ifLeft = { error -> error("${test.name}: Parse failed: $error") },
        ifRight = { definitions ->
          val testType = definitions.find { it.name == "TestType" }!!
          val valueProp = testType.properties.find { it.name == "value" }!!

          valueProp.type shouldBe test.expectedType
          valueProp.format shouldBe test.expectedFormat
        }
      )
    }
  }
})
