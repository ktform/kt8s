/*
 * Copyright (C) 2016-2025 Yuriy Yarosh
 * All rights reserved.
 *
 * SPDX-License-Identifier: MPL-2.0
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package dev.ktform.kt8s.compiler

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JSONSchemaTest {

    data class SchemaTestCase(
        val name: String,
        val schema: String,
        val expectError: Boolean = false,
        val expectedErrorType: ((JSONSchema.ParseError) -> Boolean)? = null,
        val expectedDefinitionCount: Int = 1,
        val validate: (List<JSONSchema.Definition>) -> Either<String, Unit> = { Either.Right(Unit) },
    )

    private fun runSchemaTestCase(testCase: SchemaTestCase) {
        JSONSchema.parseSchemaString(testCase.schema)
            .fold(
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
                        testCase
                            .validate(definitions)
                            .fold(
                                ifLeft = { errorMsg -> throw AssertionError(errorMsg) },
                                ifRight = { /* success */ },
                            )
                    }
                },
            )
    }

    @Test
    fun testBasicObjectWithStringAndIntegerProperties() {
        val testCase =
            SchemaTestCase(
                name = "basic object with string and integer properties",
                schema =
                    """
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
      """
                        .trimIndent(),
            ) { definitions ->
                either {
                    val testObject =
                        definitions
                            .find { it.name == "TestObject" }
                            .toOption()
                            .toEither { "TestObject definition not found" }
                            .bind()

                    ensure(testObject.properties.size == 2) {
                        "Expected 2 properties, got ${testObject.properties.size}"
                    }
                    ensure(testObject.properties[0].type == JSONSchema.Type.STRING) {
                        "First property should be STRING"
                    }
                    ensure(testObject.properties[1].type == JSONSchema.Type.INTEGER) {
                        "Second property should be INTEGER"
                    }
                }
            }
        runSchemaTestCase(testCase)
    }

    @Test
    fun testStringFormats() {
        val testCase =
            SchemaTestCase(
                name = "string formats (date-time, byte)",
                schema =
                    """
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
      """
                        .trimIndent(),
            ) { definitions ->
                either {
                    val obj =
                        definitions
                            .find { it.name == "FormattedObject" }
                            .toOption()
                            .toEither { "FormattedObject not found" }
                            .bind()

                    val timestampProp =
                        obj.properties
                            .find { it.name == "timestamp" }
                            .toOption()
                            .toEither { "timestamp property not found" }
                            .bind()
                    val dataProp =
                        obj.properties
                            .find { it.name == "data" }
                            .toOption()
                            .toEither { "data property not found" }
                            .bind()

                    ensure(timestampProp.format == Some(JSONSchema.Format.DATE_TIME)) {
                        "timestamp format mismatch"
                    }
                    ensure(dataProp.format == Some(JSONSchema.Format.BYTE)) {
                        "data format mismatch"
                    }
                }
            }
        runSchemaTestCase(testCase)
    }

    @Test
    fun testNumberFormats() {
        val testCase =
            SchemaTestCase(
                name = "number formats (int32, int64, double, float->double)",
                schema =
                    """
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
      """
                        .trimIndent(),
            ) { definitions ->
                either {
                    val obj =
                        definitions
                            .find { it.name == "NumberObject" }
                            .toOption()
                            .toEither { "NumberObject not found" }
                            .bind()

                    val int32Prop = obj.properties.find { it.name == "int32Val" }!!
                    val int64Prop = obj.properties.find { it.name == "int64Val" }!!
                    val doubleProp = obj.properties.find { it.name == "doubleVal" }!!
                    val floatProp = obj.properties.find { it.name == "floatVal" }!!

                    ensure(int32Prop.format == Some(JSONSchema.Format.INT32)) {
                        "int32 format mismatch"
                    }
                    ensure(int64Prop.format == Some(JSONSchema.Format.INT64)) {
                        "int64 format mismatch"
                    }
                    ensure(doubleProp.format == Some(JSONSchema.Format.DOUBLE)) {
                        "double format mismatch"
                    }
                    ensure(floatProp.format == Some(JSONSchema.Format.DOUBLE)) {
                        "float should convert to double"
                    }
                }
            }
        runSchemaTestCase(testCase)
    }

    @Test
    fun testObjectReferencesAndArrays() {
        val testCase =
            SchemaTestCase(
                name = "object references and arrays",
                schema =
                    $$"""
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
      """
                        .trimIndent(),
                expectedDefinitionCount = 2,
            ) { definitions ->
                either {
                    val container =
                        definitions
                            .find { it.name == "Container" }
                            .toOption()
                            .toEither { "Container not found" }
                            .bind()

                    val itemsProp = container.properties.find { it.name == "items" }!!
                    val addressProp = container.properties.find { it.name == "address" }!!

                    ensure(itemsProp.type == JSONSchema.Type.ARRAY) { "items should be ARRAY type" }
                    ensure(itemsProp.items.isSome()) { "array items should be defined" }
                    ensure(itemsProp.items.getOrNull()?.type == JSONSchema.Type.STRING) {
                        "array items should be STRING"
                    }

                    ensure(addressProp.type == JSONSchema.Type.OBJECT) {
                        "address should be OBJECT type"
                    }
                    ensure(addressProp.isObjectReference()) {
                        "address should be an object reference"
                    }
                    ensure(addressProp.ref == Some("Address")) {
                        "address ref should point to Address"
                    }

                    // Verify type name resolution
                    val packageName = "dev.ktform.kt8s.resources"
                    val typeName = addressProp.getTypeName(packageName)
                    ensure(typeName.toString().endsWith("Address")) {
                        "address should resolve to Address type, got: $typeName"
                    }
                }
            }
        runSchemaTestCase(testCase)
    }

    @Test
    fun testInvalidJson() {
        val testCase =
            SchemaTestCase(
                name = "invalid JSON",
                schema = "{ invalid }",
                expectError = true,
                expectedErrorType = { it is JSONSchema.ParseError.InvalidSchema },
            )
        runSchemaTestCase(testCase)
    }

    @Test
    fun testMissingDefinitions() {
        val testCase =
            SchemaTestCase(
                name = "missing definitions",
                schema = """{"other": "value"}""",
                expectError = true,
                expectedErrorType = { it is JSONSchema.ParseError.MissingDefinitions },
            )
        runSchemaTestCase(testCase)
    }

    @Test
    fun testUnsupportedType() {
        val testCase =
            SchemaTestCase(
                name = "unsupported type",
                schema =
                    """
        {"definitions": {"Bad": {"type": "object", "properties": {"bad": {"type": "unknown"}}}}}
      """
                        .trimIndent(),
                expectError = true,
                expectedErrorType = { it is JSONSchema.ParseError.UnsupportedType },
            )
        runSchemaTestCase(testCase)
    }

    @Test
    fun testArrayWithoutItems() {
        val testCase =
            SchemaTestCase(
                name = "array without items",
                schema =
                    """
        {"definitions": {"Bad": {"type": "object", "properties": {"arr": {"type": "array"}}}}}
      """
                        .trimIndent(),
                expectError = true,
                expectedErrorType = { it is JSONSchema.ParseError.InvalidProperty },
            )
        runSchemaTestCase(testCase)
    }

    @Test
    fun testAllSchemaVersionsShouldBeLoadable() {
        val (failed, successful) =
            JSONSchema.Version.entries
                .map { version -> version to JSONSchema.load(version) }
                .partition { (_, result) -> result.isLeft() }

        // Log successful loads
        successful.forEach { (version, result) ->
            result.fold(
                ifLeft = { /* should not happen */ },
                ifRight = { definitions ->
                    println("✓ ${version.value}: ${definitions.size} definitions")
                },
            )
        }

        // Verify failed loads have expected error types
        failed.forEach { (version, result) ->
            result.fold(
                ifLeft = { error ->
                    assertTrue(
                        error is JSONSchema.ParseError.FileError ||
                            error is JSONSchema.ParseError.InvalidSchema
                    )
                },
                ifRight = { error("Failed to load ${version.value}") },
            )
        }

        assertTrue(failed.isEmpty())
        assertEquals(JSONSchema.Version.entries.size, successful.size + failed.size)
    }

    @Test
    fun testReferenceResolution() {
        val version = JSONSchema.Version.V1_32_0

        JSONSchema.load(version)
            .fold(
                ifLeft = { error ->
                    error("Failed to load schema version ${version.value}: $error")
                },
                ifRight = { definitions ->
                    val objectRefs = mutableListOf<Pair<String, String>>()

                    // Collect all object references
                    definitions.forEach { definition ->
                        definition.properties.forEach { property ->
                            if (property.isObjectReference()) {
                                property.ref.fold(
                                    {
                                        throw AssertionError(
                                            "Property ${property.name} in ${definition.name} is object reference but no ref value"
                                        )
                                    },
                                    { refValue -> objectRefs.add(definition.name to refValue) },
                                )
                            }
                        }
                    }

                    assertTrue(objectRefs.isNotEmpty())
                    println("Found ${objectRefs.size} object references")

                    // Verify all references can be resolved by finding matching definitions
                    objectRefs.forEach { (fromDef, toDef) ->
                        val resolved = definitions.find { it.name == toDef }
                        assertTrue(
                            resolved != null,
                            "Could not resolve reference from $fromDef to $toDef",
                        )
                        assertEquals(toDef, resolved?.name)
                    }

                    // Test dynamic type resolution
                    val packageName = "dev.ktform.kt8s.resources"
                    objectRefs.take(5).forEach { (fromDef, toDef) ->
                        val definition = definitions.find { it.name == fromDef }!!
                        val property =
                            definition.properties.find {
                                it.isObjectReference() && it.ref == Some(toDef)
                            }!!
                        val typeName = property.getTypeName(packageName)
                        assertTrue(
                            typeName.toString().endsWith(toDef.split(".").last()),
                            "Type resolution failed: expected to end with ${toDef.split(".").last()}, got $typeName",
                        )
                    }
                },
            )
    }

    @Test
    fun testAllResourcesAreUniqueAcrossVersions() {
        val allDefinitionsByVersion = JSONSchema.all(this::class.java.classLoader)

        // Collect all resource names by version
        val resourceNamesByVersion =
            allDefinitionsByVersion.mapValues { (_, definitions) ->
                definitions.map { it.name }.toSet()
            }

        val commonCount = resourceNamesByVersion["Common"]?.size ?: 0
        val versionSpecificCount =
            resourceNamesByVersion.filterKeys { it != "Common" }.values.sumOf { it.size }

        assertTrue(commonCount > 0, "Common definitions should not be empty")
        assertTrue(versionSpecificCount > 0, "Version-specific definitions should not be empty")

        // Verify no resource name appears in multiple versions
        val allResourceNames = mutableSetOf<String>()
        val duplicates = mutableListOf<String>()

        resourceNamesByVersion.forEach { (version, names) ->
            names.forEach { name ->
                if (name in allResourceNames) {
                    duplicates.add("Resource '$name' appears in multiple versions")
                }
                allResourceNames.add(name)
            }
        }

        assertTrue(
            duplicates.isEmpty(),
            "Found duplicate resources across versions:\n${duplicates.joinToString("\n")}",
        )

        println("✓ Common definitions: $commonCount")
        println("✓ Version-specific definitions: $versionSpecificCount")
        println("✓ Total unique resources: ${allResourceNames.size}")
        println("✓ All resources are unique across versions")
    }

    @Test
    fun testObjectRefs() {
        val allDefinitionsByVersion = JSONSchema.all(this::class.java.classLoader)

        val allProperties = allDefinitionsByVersion.values.flatten().flatMap { it.properties }

        val objectRefProperties = allProperties.filter { it.isObjectReference() }
        val packageName = "dev.ktform.kt8s.resources"

        // Verify object references have proper type names
        val improperRefs =
            objectRefProperties.filter { property ->
                val typeName = property.getTypeName(packageName)
                typeName.toString() == "kotlin.Any" || typeName.toString().contains("ANY")
            }

        assertTrue(
            improperRefs.isEmpty(),
            "Found ${improperRefs.size} object references with improper types:\n" +
                improperRefs.joinToString("\n") { "${it.name}: ${it.getTypeName(packageName)}" },
        )

        println("✓ All ${objectRefProperties.size} object references have proper type resolution")

        // Verify some known references
        val metadataProps = allProperties.filter { it.name == "metadata" && it.isObjectReference() }
        assertTrue(metadataProps.isNotEmpty(), "Should have metadata properties")

        // Kubernetes has different metadata types: ObjectMeta for resources, ListMeta for lists
        val validMetadataTypes = setOf("ObjectMeta", "ListMeta")
        val metadataTypesSeen = mutableSetOf<String>()

        metadataProps.forEach { prop ->
            val typeName = prop.getTypeName(packageName)
            val className = typeName.toString().split(".").last()
            metadataTypesSeen.add(className)

            assertTrue(
                validMetadataTypes.contains(className),
                "Metadata should resolve to ObjectMeta or ListMeta, got: $typeName",
            )
        }

        println(
            "✓ Verified metadata properties resolve to valid types: ${metadataTypesSeen.sorted()}"
        )
    }
}
