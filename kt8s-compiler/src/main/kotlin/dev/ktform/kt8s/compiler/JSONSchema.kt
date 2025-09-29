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
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import java.io.File
import kotlinx.serialization.json.*

object JSONSchema {
    enum class Version(val value: String) {
        V1_20_0("1.20.0"),
        V1_21_0("1.21.0"),
        V1_22_0("1.22.0"),
        V1_23_0("1.23.0"),
        V1_24_0("1.24.0"),
        V1_25_0("1.25.0"),
        V1_26_0("1.26.0"),
        V1_27_0("1.27.0"),
        V1_28_0("1.28.0"),
        V1_29_0("1.29.0"),
        V1_30_0("1.30.0"),
        V1_31_0("1.31.0"),
        V1_32_0("1.32.0"),
        V1_33_0("1.33.0");

        companion object {
            val all = entries
        }
    }

    enum class Type(val typeRef: TypeName) {
        OBJECT(ClassName("dev.ktform.kt8s.resources", "RawJsonObject")),
        ARRAY(com.squareup.kotlinpoet.ANY),
        STRING(com.squareup.kotlinpoet.STRING),
        BOOLEAN(com.squareup.kotlinpoet.BOOLEAN),
        NUMBER(com.squareup.kotlinpoet.DOUBLE),
        INTEGER(com.squareup.kotlinpoet.INT),
        TIME(ClassName("dev.ktform.kt8s.resources", "KubernetesTime")),
        MICROTIME(ClassName("dev.ktform.kt8s.resources", "KubernetesMicroTime")),
        STRINGMAP(
            ClassName("kotlin.collections", "Map")
                .parameterizedBy(com.squareup.kotlinpoet.STRING, com.squareup.kotlinpoet.STRING)
        ),
        INT_OR_STRING(ClassName("dev.ktform.kt8s.resources", "IntOrString")),
        STRING_OR_NUMBER(ClassName("dev.ktform.kt8s.resources", "StringOrNumber")),
    }

    enum class Format(val type: Type, val formatName: String, val typeRef: ClassName) {
        // Number formats
        INT32(Type.INTEGER, "int32", com.squareup.kotlinpoet.INT),
        INT64(Type.INTEGER, "int64", com.squareup.kotlinpoet.LONG),
        DOUBLE(Type.NUMBER, "double", com.squareup.kotlinpoet.DOUBLE),

        // String formats
        BYTE(Type.STRING, "byte", com.squareup.kotlinpoet.STRING),
        DATE_TIME(Type.STRING, "date-time", com.squareup.kotlinpoet.STRING);

        companion object {
            fun fromString(format: String): Option<Format> =
                entries.find { it.formatName == format }.toOption()
        }
    }

    class Definition(
        val name: String,
        val description: String,
        val properties: List<Property>,
        val required: Set<String> = emptySet(),
        val type: Type = Type.OBJECT,
        val format: Option<Format> = None,
        val kubernetesGroupVersionKind: List<Triple<String, String, String>> = emptyList(),
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Definition) return false

            return name == other.name
        }

        override fun hashCode(): Int {
            return kubernetesGroupVersionKind.hashCode()
        }
    }

    class Property(
        val name: String,
        val type: Type,
        val format: Option<Format> = None,
        val description: String = "",
        val required: Boolean = false,
        val enum: List<String> = emptyList(),
        val items: Option<Property> = None,
        val ref: Option<String> = None,
    ) {
        fun isObjectReference(): Boolean = ref.isSome()

        fun getTypeName(packageName: String): TypeName =
            when {
                isObjectReference() -> {
                    val refName = ref.getOrElse { "" }
                    val className = refName.split(".").last()
                    ClassName(packageName, className)
                }

                format.isSome() -> format.getOrNull()!!.typeRef
                else -> type.typeRef
            }
    }

    sealed class ParseError {
        class InvalidSchema(val message: String) : ParseError() {
            override fun toString(): String = "Invalid schema: $message"
        }

        class MissingDefinitions(val message: String) : ParseError() {
            override fun toString(): String = "Missing definitions: $message"
        }

        class InvalidProperty(val message: String) : ParseError() {
            override fun toString(): String = "Invalid property: $message"
        }

        class UnsupportedType(val type: String) : ParseError() {
            override fun toString(): String = "Unsupported type: $type"
        }

        class FileError(val message: String) : ParseError() {
            override fun toString(): String = "File error: $message"
        }
    }

    fun load(
        version: Version,
        classLoader: ClassLoader = this::class.java.classLoader,
    ): Either<ParseError, List<Definition>> {
        val resourcePath = "k8s/${version.value}.json"
        val classLoaders =
            listOf(
                classLoader,
                this::class.java.classLoader,
                Thread.currentThread().contextClassLoader,
                ClassLoader.getSystemClassLoader(),
            )

        return classLoaders
            .asSequence()
            .mapNotNull { cl ->
                cl?.getResourceAsStream(resourcePath)?.use { stream ->
                    try {
                        parseSchemaString(stream.readBytes().decodeToString())
                    } catch (e: Exception) {
                        Either.Left(
                            ParseError.FileError("Failed to load ${version.value}: ${e.message}")
                        )
                    }
                }
            }
            .firstOrNull()
            ?: Either.Left(ParseError.FileError("Schema not found: ${version.value}"))
    }

    internal fun parseSchemaFile(file: File): Either<ParseError, List<Definition>> = either {
        ensure(file.exists() && file.canRead()) {
            ParseError.FileError("Cannot read: ${file.absolutePath}")
        }
        parseSchemaString(file.readText()).bind()
    }

    internal fun parseSchemaString(jsonString: String): Either<ParseError, List<Definition>> =
        either {
            val json =
                kotlin
                    .runCatching { Json.parseToJsonElement(jsonString).jsonObject }
                    .getOrElse { raise(ParseError.InvalidSchema("Invalid JSON: ${it.message}")) }

            val definitionsObj =
                json["definitions"]?.jsonObject
                    ?: raise(ParseError.MissingDefinitions("Missing 'definitions'"))

            definitionsObj.map { (name, defJson) -> defJson.jsonObject.toDefinition(name).bind() }
        }
}

internal fun JsonObject.toDefinition(
    name: String
): Either<JSONSchema.ParseError, JSONSchema.Definition> = either {
    val (type, format) = this@toDefinition.parseTypeAndFormat().bind()
    val required =
        this@toDefinition["required"]?.jsonArray?.map { it.jsonPrimitive.content }?.toSet()
            ?: emptySet()
    val properties =
        this@toDefinition["properties"]?.jsonObject?.map { (propName, propJson) ->
            propJson.jsonObject.toProperty(propName, propName in required).bind()
        } ?: emptyList()

    JSONSchema.Definition(
        name = name,
        description = this@toDefinition["description"]?.jsonPrimitive?.content.orEmpty(),
        properties = properties,
        required = required,
        type = type,
        format = format,
        kubernetesGroupVersionKind = this@toDefinition.parseKubernetesGVK(name),
    )
}

private fun JsonObject.toProperty(
    name: String,
    isRequired: Boolean,
): Either<JSONSchema.ParseError, JSONSchema.Property> = either {
    // Handle direct $ref
    this@toProperty["\$ref"]?.jsonPrimitive?.content?.let {
        return@either JSONSchema.Property(
            name = name,
            type = JSONSchema.Type.OBJECT,
            description = this@toProperty["description"]?.jsonPrimitive?.content.orEmpty(),
            required = isRequired,
            ref = Some(it.removePrefix("#/definitions/")),
        )
    }

    // Check for additionalProperties with $ref (like Map<String, Quantity>)
    this@toProperty["additionalProperties"]?.jsonObject?.let { additionalProps ->
        additionalProps["\$ref"]?.jsonPrimitive?.content?.let { refValue ->
            return@either JSONSchema.Property(
                name = name,
                type = JSONSchema.Type.OBJECT,
                description = this@toProperty["description"]?.jsonPrimitive?.content.orEmpty(),
                required = isRequired,
                ref = Some(refValue.removePrefix("#/definitions/")),
            )
        }
    }

    val (type, format) = this@toProperty.parseTypeAndFormat().bind()

    JSONSchema.Property(
        name = name,
        type = type,
        format = format,
        description = this@toProperty["description"]?.jsonPrimitive?.content.orEmpty(),
        required = isRequired,
        enum = this@toProperty["enum"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
        items =
            if (type == JSONSchema.Type.ARRAY) {
                val itemsObj =
                    this@toProperty["items"]?.jsonObject
                        ?: raise(JSONSchema.ParseError.InvalidProperty("Array needs 'items'"))
                Some(itemsObj.toProperty("item", false).bind())
            } else None,
    )
}

private fun JsonObject.parseTypeAndFormat():
    Either<JSONSchema.ParseError, Pair<JSONSchema.Type, Option<JSONSchema.Format>>> = either {
    val format =
        this@parseTypeAndFormat["format"]
            ?.jsonPrimitive
            ?.content
            .toOption()
            .map { if (it == "float") "double" else it }
            .flatMap(JSONSchema.Format::fromString)

    val type =
        when (val typeStr = this@parseTypeAndFormat["type"]?.jsonPrimitive?.content) {
            "object" -> JSONSchema.Type.OBJECT
            "array" -> JSONSchema.Type.ARRAY
            "string" -> JSONSchema.Type.STRING
            "boolean" -> JSONSchema.Type.BOOLEAN
            "number" -> format.fold({ JSONSchema.Type.NUMBER }) { it.type }
            "integer" -> JSONSchema.Type.INTEGER
            null ->
                if (this@parseTypeAndFormat.containsKey("\$ref")) JSONSchema.Type.OBJECT
                else JSONSchema.Type.OBJECT

            else -> raise(JSONSchema.ParseError.UnsupportedType(typeStr))
        }

    type to format
}

internal fun JsonObject.parseKubernetesGVK(refName: String): List<Triple<String, String, String>> {
    val chunks = refName.split(".")
    val (defaultKind, defaultVersion, defaultGroup) =
        if (chunks.size >= 3) {
            Triple(chunks.last(), chunks.dropLast(1).last(), chunks.dropLast(2).joinToString("."))
        } else {
            Triple("", "", "")
        }

    return this["x-kubernetes-group-version-kind"]?.jsonArray?.map { gvkElement ->
        val gvk = gvkElement.jsonObject
        Triple(
            gvk["group"]?.jsonPrimitive?.content ?: defaultGroup,
            gvk["version"]?.jsonPrimitive?.content ?: defaultVersion,
            gvk["kind"]?.jsonPrimitive?.content ?: defaultKind,
        )
    } ?: listOf(Triple(defaultGroup, defaultVersion, defaultKind))
}

internal fun List<JSONSchema.Definition>.resolveReference(
    ref: String
): Option<JSONSchema.Definition> = find { it.name == ref }.toOption()

private val skippedKinds = setOf("ParamKind", "JSONSchemaProps")

fun List<JSONSchema.Definition>.toResources(packageName: String): List<KubernetesResource> =
    asSequence()
        .map { KubernetesResource.fromJsonSchema(it, this@toResources, packageName) }
        .filter { it.fields.isNotEmpty() && it.kind !in skippedKinds }
        .distinctBy { it.kind }
        .toList()

fun JSONSchema.all(
    classLoader: ClassLoader = this::class.java.classLoader
): Map<String, List<JSONSchema.Definition>> {
    val loadedResources =
        JSONSchema.Version.all.associate { version ->
            version.name to
                JSONSchema.load(version, classLoader).getOrElse {
                    throw IllegalArgumentException("Failed to load ${version.name} resources")
                }
        }

    val allDefinitions = loadedResources.values.toList()
    val common =
        allDefinitions
            .takeIf { it.isNotEmpty() }
            ?.first()
            ?.filter { candidate -> allDefinitions.all { defs -> defs.any { it == candidate } } }
            ?.distinctBy { it.name } ?: emptyList()

    val (versionSpecific, _) =
        JSONSchema.Version.all.fold(
            emptyMap<String, List<JSONSchema.Definition>>() to emptySet<String>()
        ) { (result, seenNames), version ->
            val current = loadedResources[version.name] ?: emptyList()
            val unique =
                current
                    .asSequence()
                    .filterNot { def -> common.any { it == def } || def.name in seenNames }
                    .distinctBy { it.name }
                    .toList()
            (result + (version.name to unique)) to (seenNames + unique.map { it.name })
        }

    return mapOf("Common" to common) + versionSpecific
}
