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

import arrow.core.raise.either
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import dev.ktform.kt8s.compiler.JSONSchema.toResources
import io.ktor.util.*
import java.io.OutputStreamWriter

class KubernetesResourcesSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (
            resolver.getClassDeclarationByName(
                resolver.getKSNameFromString("dev.ktform.kt8s.resources.Container")
            ) != null
        )
            return emptyList()

        return either<Throwable, Unit> {
                val standardResources =
                    JSONSchema.all(this::class.java.classLoader).mapKeys { (version, _) ->
                        if (version == "Common") "dev.ktform.kt8s.resources"
                        else
                            "dev.ktform.kt8s.resources.${version.toLowerCasePreservingASCIIRules()}"
                    }

                val crdResources =
                    Crds.all(this::class.java.classLoader)
                        .fold(
                            { err ->
                                logger.error("CRD loading failed: $err")
                                emptyMap()
                            },
                            {
                                it.mapKeys { (chartName, _) ->
                                    "dev.ktform.kt8s.resources.crds.${chartName.toLowerCasePreservingASCIIRules()}"
                                }
                            },
                        )

                (standardResources + crdResources).forEach { (pkg, definitions) ->
                    logger.info("Generating ${definitions.size} definitions in $pkg")
                    definitions.toResources(pkg).forEach { resource ->
                        codeGenerator.createNewFile(Dependencies(false), pkg, resource.kind).use {
                            outputStream ->
                            OutputStreamWriter(outputStream).use { writer ->
                                writer.write(resource.getFileBuilder().build().toString())
                                writer.flush()
                            }
                        }
                    }
                }
            }
            .fold(
                {
                    logger.error(
                        "KubernetesResourcesSymbolProcessor: code generation error: ${it.message}"
                    )
                    emptyList()
                },
                { emptyList() },
            )
    }
}
