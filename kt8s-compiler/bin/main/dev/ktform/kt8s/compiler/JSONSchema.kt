package dev.ktform.kt8s.compiler

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.serialization.json.*
import java.io.File

object JSONSchema {
  enum class Version(val value: String) {
    V1_20_0("1.20.0"), V1_21_0("1.21.0"), V1_22_0("1.22.0"), V1_23_0("1.23.0"),
    V1_24_0("1.24.0"), V1_25_0("1.25.0"), V1_26_0("1.26.0"), V1_27_0("1.27.0"),
    V1_28_0("1.28.0"), V1_29_0("1.29.0"), V1_30_0("1.30.0"), V1_31_0("1.31.0"),
    V1_32_0("1.32.0"), V1_33_0("1.33.0")
  }

  enum class Type { OBJECT, ARRAY, STRING, BOOLEAN, NUMBER, INTEGER }

  enum class Format(val type: Type, val formatName: String) {
    OBJECT_REF(Type.OBJECT, "object-ref"),

    // Number formats
    INT32(Type.INTEGER, "int32"),
    INT64(Type.INTEGER, "int64"),
    DOUBLE(Type.NUMBER, "double"),

    // String formats
    BYTE(Type.STRING, "byte"),
    DATE_TIME(Type.STRING, "date-time");

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
  )

  class Property(
    val name: String,
    val type: Type,
    val format: Option<Format> = None,
    val description: String = "",
    val required: Boolean = false,
    val enum: List<String> = emptyList(),
    val items: Option<Property> = None,
    val ref: Option<String> = None,
  )

  sealed class ParseError {
    class InvalidSchema(val message: String) : ParseError()
    class MissingDefinitions(val message: String) : ParseError()
    class InvalidProperty(val message: String) : ParseError()
    class UnsupportedType(val type: String) : ParseError()
    class FileError(val message: String) : ParseError()
  }

  fun load(version: Version): Either<ParseError, List<Definition>> =
    this::class.java.classLoader.getResource("k8s/${version.value}.json")?.let {
      try {
        parseSchemaFile(File(it.toURI()))
      } catch (e: Exception) {
        Either.Left(ParseError.FileError("Failed to load ${version.value}: ${e.message}"))
      }
    } ?: Either.Left(ParseError.FileError("Schema not found: ${version.value}"))

  internal fun parseSchemaFile(file: File): Either<ParseError, List<Definition>> = either {
    ensure(file.exists() && file.canRead()) { ParseError.FileError("Cannot read: ${file.absolutePath}") }
    parseSchemaString(file.readText()).bind()
  }

  internal fun parseSchemaString(jsonString: String): Either<ParseError, List<Definition>> = either {
    val json = kotlin.runCatching { Json.parseToJsonElement(jsonString).jsonObject }
      .getOrElse { raise(ParseError.InvalidSchema("Invalid JSON: ${it.message}")) }

    val definitionsObj = json["definitions"]?.jsonObject
      ?: raise(ParseError.MissingDefinitions("Missing 'definitions'"))

    definitionsObj.map { (name, defJson) ->
      defJson.jsonObject.toDefinition(name).bind()
    }
  }

  private fun JsonObject.toDefinition(name: String): Either<ParseError, Definition> = either {
      val (type, format) = this@toDefinition.parseTypeAndFormat().bind()
      val required = this@toDefinition["required"]?.jsonArray?.map { it.jsonPrimitive.content }?.toSet() ?: emptySet()
      val properties = this@toDefinition["properties"]?.jsonObject?.map { (propName, propJson) ->
        propJson.jsonObject.toProperty(propName, propName in required).bind()
      } ?: emptyList()

      Definition(
        name = name,
        description = this@toDefinition["description"]?.jsonPrimitive?.content.orEmpty(),
        properties = properties,
        required = required,
        type = type,
        format = format,
        kubernetesGroupVersionKind = this@toDefinition.parseKubernetesGVK(),
      )
    }

  private fun JsonObject.toProperty(name: String, isRequired: Boolean): Either<ParseError, Property> = either {
      this@toProperty[$$"$ref"]?.jsonPrimitive?.content?.let {
        return@either Property(
          name = name,
          type = Type.OBJECT,
          format = Some(Format.OBJECT_REF),
          description = this@toProperty["description"]?.jsonPrimitive?.content.orEmpty(),
          required = isRequired,
          ref = Some(it.removePrefix("#/definitions/")),
        )
      }

      val (type, format) = this@toProperty.parseTypeAndFormat().bind()

      Property(
        name = name,
        type = type,
        format = format,
        description = this@toProperty["description"]?.jsonPrimitive?.content.orEmpty(),
        required = isRequired,
        enum = this@toProperty["enum"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
        items = if (type == Type.ARRAY) {
          val itemsObj = this@toProperty["items"]?.jsonObject ?: raise(ParseError.InvalidProperty("Array needs 'items'"))
          Some(itemsObj.toProperty("item", false).bind())
        } else None,
      )
    }

  private fun JsonObject.parseTypeAndFormat(): Either<ParseError, Pair<Type, Option<Format>>> =
    either {
      val format = this@parseTypeAndFormat["format"]
        ?.jsonPrimitive
        ?.content
        .toOption()
        .map { if (it == "float") "double" else it }
        .flatMap(Format::fromString)

      val type = when (val typeStr = this@parseTypeAndFormat["type"]?.jsonPrimitive?.content) {
        "object" -> Type.OBJECT
        "array" -> Type.ARRAY
        "string" -> Type.STRING
        "boolean" -> Type.BOOLEAN
        "number" -> format.fold({ Type.NUMBER }) { it.type }
        "integer" -> Type.INTEGER
        null -> if (this@parseTypeAndFormat.containsKey($$"$ref")) Type.OBJECT else Type.OBJECT
        else -> raise(ParseError.UnsupportedType(typeStr))
      }

      type to format
    }

  private fun JsonObject.parseKubernetesGVK(): List<Triple<String, String, String>> =
    this["x-kubernetes-group-version-kind"]?.jsonArray?.map { gvkElement ->
      val gvk = gvkElement.jsonObject
      Triple(
        gvk["group"]?.jsonPrimitive?.content.orEmpty(),
        gvk["version"]?.jsonPrimitive?.content.orEmpty(),
        gvk["kind"]?.jsonPrimitive?.content.orEmpty(),
      )
    } ?: emptyList()

  // Extension functions for List<Definition>
  fun List<Definition>.resolveReference(ref: String): Option<Definition> =
    find { it.name == ref }.toOption()

  fun List<Definition>.getDefinitionsByKind(kind: String): List<Definition> =
    filter { it.kubernetesGroupVersionKind.any { (_, _, k) -> k == kind } }

  fun List<Definition>.getDefinitionsByGroupVersion(group: String, version: String): List<Definition> =
    filter { it.kubernetesGroupVersionKind.any { (g, v, _) -> g == group && v == version } }
}
