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

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

sealed class KubernetesResourceField(
    val name: String,
    val description: String,
    val isArray: Boolean,
) {
    abstract fun toPropertySpec(pkg: String): Option<PropertySpec>

    abstract fun toParamSpec(pkg: String): Option<ParameterSpec>

    abstract fun referencedResourceName(): Option<String>

    protected fun wrapIfArray(base: TypeName): TypeName =
        if (isArray) LIST.parameterizedBy(base) else base

    protected fun propertyOf(type: TypeName): PropertySpec =
        PropertySpec.builder(name, type).initializer(name).build()

    protected fun paramOf(type: TypeName): ParameterSpec = ParameterSpec.builder(name, type).build()

    data class Plain(
        val type: JSONSchema.Type,
        val format: Option<JSONSchema.Format>,
        private val fieldName: String,
        private val fieldDescription: String,
        private val fieldIsArray: Boolean,
    ) : KubernetesResourceField(fieldName, fieldDescription, fieldIsArray) {
        private val baseType = format.fold({ type.typeRef }) { it.typeRef }

        override fun toPropertySpec(pkg: String) = Some(propertyOf(wrapIfArray(baseType)))

        override fun toParamSpec(pkg: String) = Some(paramOf(wrapIfArray(baseType)))

        override fun referencedResourceName() = None
    }

    data class Enum(
        val type: JSONSchema.Type,
        val variants: List<String>,
        private val fieldName: String,
        private val fieldDescription: String,
        private val fieldIsArray: Boolean,
    ) : KubernetesResourceField(fieldName, fieldDescription, fieldIsArray) {
        // Represent enum as String for now; enum codegen can be added later
        override fun toPropertySpec(pkg: String) = Some(propertyOf(wrapIfArray(STRING)))

        override fun toParamSpec(pkg: String) = Some(paramOf(wrapIfArray(STRING)))

        override fun referencedResourceName() = None
    }

    data class Resource(
        val resourceName: String,
        private val fieldName: String,
        private val fieldDescription: String,
        private val fieldIsArray: Boolean,
    ) : KubernetesResourceField(fieldName, fieldDescription, fieldIsArray) {
        private val simpleClassName = resourceName.substringAfterLast(".")

        private fun baseType(pkg: String) = ClassName(pkg, simpleClassName)

        override fun toPropertySpec(pkg: String) = Some(propertyOf(wrapIfArray(baseType(pkg))))

        override fun toParamSpec(pkg: String) = Some(paramOf(wrapIfArray(baseType(pkg))))

        override fun referencedResourceName() = Some(resourceName)
    }

    companion object {
        private val LIST = ClassName("kotlin.collections", "List")

        fun fromJsonSchema(
            property: JSONSchema.Property,
            all: List<JSONSchema.Definition>,
        ): KubernetesResourceField {
            if (property.name in setOf("labels", "annotations")) {
                return Plain(
                    JSONSchema.Type.STRINGMAP,
                    None,
                    property.name,
                    property.description,
                    false,
                )
            }

            if (property.name in setOf("binaryData", "data")) {
                return Plain(
                    JSONSchema.Type.OBJECT,
                    None,
                    property.name,
                    property.description,
                    false,
                )
            }

            if (property.name in setOf("stringData")) {
                return Plain(
                    JSONSchema.Type.STRING,
                    None,
                    property.name,
                    property.description,
                    false,
                )
            }

            fun refToCustomPlain(refName: String): Option<KubernetesResourceField> =
                when (refName.split(".").last()) {
                    in setOf(
                        "FieldsV1",
                        "JSON",
                        "CustomResourceSubresourceStatus",
                        "JSONSchemaPropsOrArray",
                        "JSONSchemaProps",
                        "JSONSchemaPropsOrBool",
                        "JSONSchemaPropsOrStringArray",
                        "StorageVersionSpec",
                        "RawExtension",
                        "ParamKind",
                    ) ->
                        Some(
                            Plain(
                                JSONSchema.Type.OBJECT,
                                None,
                                property.name,
                                property.description,
                                false,
                            )
                        )

                    "Quantity" ->
                        Some(
                            Plain(
                                JSONSchema.Type.STRING_OR_NUMBER,
                                None,
                                property.name,
                                property.description,
                                false,
                            )
                        )

                    "IntOrString" ->
                        Some(
                            Plain(
                                JSONSchema.Type.INT_OR_STRING,
                                None,
                                property.name,
                                property.description,
                                false,
                            )
                        )

                    "Time" ->
                        Some(
                            Plain(
                                JSONSchema.Type.TIME,
                                None,
                                property.name,
                                property.description,
                                false,
                            )
                        )

                    "MicroTime" ->
                        Some(
                            Plain(
                                JSONSchema.Type.MICROTIME,
                                None,
                                property.name,
                                property.description,
                                false,
                            )
                        )

                    else -> None
                }

            // Handle arrays first
            if (property.type == JSONSchema.Type.ARRAY) {
                return property.items.getOrNull()?.let { item ->
                    item.ref.getOrNull()?.let { refName ->
                        refToCustomPlain(refName).getOrNull()
                            ?: all.resolveReference(refName).getOrNull()?.let { refDef ->
                                Resource(refDef.name, property.name, property.description, true)
                            }
                    }
                        ?: if (item.enum.isNotEmpty())
                            Enum(item.type, item.enum, property.name, property.description, true)
                        else
                            Plain(item.type, item.format, property.name, property.description, true)
                } ?: Plain(JSONSchema.Type.OBJECT, None, property.name, property.description, true)
            }

            // Non-array handling
            property.ref.getOrNull()?.let { refName ->
                return refToCustomPlain(refName).getOrNull()
                    ?: all.resolveReference(refName).getOrNull()?.let { refDef ->
                        Resource(refDef.name, property.name, property.description, false)
                    }
                    ?: Plain(
                        JSONSchema.Type.OBJECT,
                        None,
                        property.name,
                        property.description,
                        false,
                    )
            }

            return if (property.enum.isNotEmpty())
                Enum(property.type, property.enum, property.name, property.description, false)
            else Plain(property.type, property.format, property.name, property.description, false)
        }
    }
}
