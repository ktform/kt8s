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

import arrow.core.firstOrNone
import arrow.core.getOrElse
import com.squareup.kotlinpoet.*
import kotlinx.serialization.Serializable

data class KubernetesResource(
    val group: String,
    val version: String,
    val kind: String,
    val packageName: String,
    val fields: List<KubernetesResourceField>,
) {
    val apiVersion: String = if (group.isEmpty()) version else "$group/$version"

    val typeSpec: TypeSpec by lazy {
        val constructorFields =
            fields.filterNot { it.name in setOf("apiVersion", "kind", "group", "version") }

        TypeSpec.classBuilder(kind)
            .addAnnotation(Serializable::class)
            .addModifiers(KModifier.DATA)
            .addSuperinterface(ClassName("dev.ktform.kt8s.resources", "Resource"))
            .primaryConstructor(buildConstructor(constructorFields))
            .addKDoc(constructorFields)
            .addProperties(constructorFields)
            .addProperty(metadataProperty("apiVersion", apiVersion, serialized = true))
            .addProperty(metadataProperty("group", group, serialized = false))
            .addProperty(metadataProperty("version", version, serialized = false))
            .addProperty(metadataProperty("kind", kind, serialized = true))
            .build()
    }

    private fun buildConstructor(fields: List<KubernetesResourceField>) =
        FunSpec.constructorBuilder()
            .addParameters(fields.mapNotNull { it.toParamSpec(packageName).getOrNull() })
            .build()

    private fun TypeSpec.Builder.addKDoc(fields: List<KubernetesResourceField>) = apply {
        val docs =
            fields
                .filter { it.description.isNotBlank() }
                .joinToString("\n") {
                    "@param ${it.name} ${it.description.replace("/*", "/\\*").replace("*/", "*\\/")}"
                }
        if (docs.isNotEmpty()) addKdoc("%L", docs)
    }

    private fun TypeSpec.Builder.addProperties(fields: List<KubernetesResourceField>) = apply {
        fields.mapNotNull { it.toPropertySpec(packageName).getOrNull() }.forEach(::addProperty)
    }

    private fun metadataProperty(
        name: String,
        vararg args: Any,
        serialized: Boolean,
        format: String = "%S",
    ) =
        PropertySpec.builder(name, STRING)
            .addModifiers(KModifier.OVERRIDE)
            .initializer(format, *args)
            .addAnnotation(
                if (serialized) {
                    AnnotationSpec.builder(ClassName("kotlinx.serialization", "SerialName"))
                        .addMember(format, name)
                        .build()
                } else {
                    AnnotationSpec.builder(ClassName("kotlinx.serialization", "Transient")).build()
                }
            )
            .build()

    internal fun getFileBuilder(): FileSpec.Builder =
        FileSpec.builder(packageName, kind)
            .addType(typeSpec)
            .addImport(
                "dev.ktform.kt8s.resources",
                "IntOrString",
                "RawJsonObject",
                "StringOrNumber",
                "KubernetesTime",
                "KubernetesMicroTime",
                "Resource",
            )

    override fun toString(): String = getFileBuilder().build().toString()

    companion object {
        fun fromJsonSchema(
            definition: JSONSchema.Definition,
            all: List<JSONSchema.Definition>,
            packageName: String,
        ): KubernetesResource {
            val (group, version, kind) =
                definition.kubernetesGroupVersionKind.firstOrNone().getOrElse {
                    throw IllegalArgumentException(
                        "Failed to parse kubernetesGroupVersionKind: ${definition.name}"
                    )
                }

            return KubernetesResource(
                group = group,
                version = version,
                kind = kind,
                packageName = packageName,
                fields =
                    definition.properties.map { KubernetesResourceField.fromJsonSchema(it, all) },
            )
        }
    }
}
